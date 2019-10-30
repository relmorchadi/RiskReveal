package com.scor.rr.service.batch;

import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.XLTOT;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.domain.riskReveal.RRLossTableHeader;
import com.scor.rr.repository.RRAnalysisRepository;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.writer.AbstractWriter;
import com.scor.rr.service.batch.writer.XMLWriter;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.PathUtils;
import com.scor.rr.util.Utils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@StepScope
public class ModellingOptionsExtractor extends AbstractWriter {

    private static final Logger log = LoggerFactory.getLogger(ModellingOptionsExtractor.class);

    @Autowired
    RRAnalysisRepository rrAnalysisRepository;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private XMLWriter xmlWriter;

    @Autowired
    private RmsService rmsService;

    @Value("${ihub.path}")
    private String iHub;

    public RepeatStatus extractModellingOptions() {

        log.debug("Starting ModelingOptionsExtractor.extract");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            String instanceId = bundle.getInstanceId();

            String modelingOptions = rmsService.getAnalysisModellingOptionSettings(instanceId, bundle.getRlAnalysis().getRdmId(),
                    bundle.getRlAnalysis().getRdmName(), bundle.getRlAnalysis().getAnalysisId());
            log.info("modelingOptions: {}", modelingOptions);

            List<String> options = null;
            try {
                options = Utils.parseXMLForModelingOptions(modelingOptions);
            } catch (DocumentException e) {
                log.error("modelingOptions DocumentException");
                e.printStackTrace();
            } catch (Exception e){
                log.error("An exception has occurred while parsing modelling options xml");
                e.printStackTrace();
            }

            bundle.setModelingOptionsOfRRLT(options);

            writeFile(bundle.getRrAnalysis(), bundle.getConformedRRLT(), modelingOptions);

            log.info("Finish import progress STEP 8 : EXTRACT_MODELING_OPTIONS for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
        }
        log.debug("ModelingOptionsExtractor.extract completed");
        return RepeatStatus.FINISHED;
    }

    private void writeFile(RRAnalysis rrAnalysis, LossDataHeader rrLossTable, String options) {
        RRAnalysis lossAnalysis = rrAnalysisRepository.findById(rrLossTable.getModelAnalysisId()).get();

        String filename = makeAPSFileName(
                rrAnalysis.getCreationDate(),
                rrAnalysis.getRegionPeril(),
                lossAnalysis.getFinancialPerspective(),
                rrLossTable.getCurrency(),
                rrLossTable.getOriginalTarget().equals(RRLossTableType.CONFORMED.toString()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                rrLossTable.getLossDataHeaderId(),
                ".xml");
        Path iHubPath = Paths.get(iHub);
        File file = PathUtils.makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename, iHubPath);
        xmlWriter.write(options, file);
        log.info("Write APS file: {}", file);
    }
}
