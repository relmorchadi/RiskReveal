package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.ihub.SourceResult;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.batch.XMLWriter;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.treaty.services.TransformationUtils;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.service.RmsDataProviderService;
import com.scor.rr.utils.Step;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class ModelingOptionsExtractor extends BaseFileWriter implements BaseModelingOptionsExtractor {

    private static final Logger log= LoggerFactory.getLogger(ModelingOptionsExtractor.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private RmsDataProviderService rmsDataProvider;

    @Autowired
    private XMLWriter xmlWriter;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    public ModelingOptionsExtractor() {
    }

    public ModelingOptionsExtractor(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public Boolean extract(){
        log.debug("Starting ModelingOptionsExtractor.extract");

        Date startDate = new Date();

        for (TransformationBundle bundle : transformationPackage.getBundles()) {

            // start new step (import progress) : step 8 EXTRACT_MODELING_OPTIONS for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(8);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

            String rdmName = bundle.getRmsAnalysis().getRdmName();
            Long rdmId = bundle.getRmsAnalysis().getRdmId();
            String anlsId = bundle.getRmsAnalysis().getAnalysisId();
            String instanceId;
            if (bundle.getRmsAnalysis().getRmsModelDatasource() != null && bundle.getRmsAnalysis().getRmsModelDatasource().getInstanceId() != null) {
                instanceId = bundle.getRmsAnalysis().getRmsModelDatasource().getInstanceId();
            } else {
                log.warn("RmsModelDatasource is null for rmsAnalysis {} - use instanceId from batch", bundle.getRmsAnalysis().getRmsAnalysisId());
                instanceId = getInstanceId();
            }

            String modelingOptions = rmsDataProvider.extractModelingOptions(instanceId, rdmId, rdmName, anlsId);
            log.info("modelingOptions: {}", modelingOptions);

            List<String> options = null;
            try {
                options = TransformationUtils.parseXMLForModelingOptions(modelingOptions);
            } catch (IOException e) {
                log.error("modelingOptions IOException, {}", e);
                e.printStackTrace();
            } catch (DocumentException e) {
                log.error("modelingOptions DocumentException, {}", e);
                e.printStackTrace();
            }

//            bundle.getSourceELTHeader().setModelingOptions(options);
//            bundle.getConformedELTHeader().setModelingOptions(options);

            bundle.setModelingOptionsOfRRLT(options);

//            writeFile(bundle.getConformedELTHeader(), modelingOptions);
            writeFile(bundle.getSourceResult(), bundle.getRrAnalysis(), bundle.getConformedRRLT(), modelingOptions);

            // finis step 8 EXTRACT_MODELING_OPTIONS for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 8 : EXTRACT_MODELING_OPTIONS for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("ModelingOptionsExtractor.extract completed");
        return true;
    }

    private void writeFile(SourceResult sourceResult, RRAnalysis rrAnalysis, RRLossTable rrLossTable, String options) {
        String filename = makeAPSFileName(
                rrAnalysis.getCreationDate(),
                rrAnalysis.getRegionPeril(),
                rrLossTable.getFinancialPerspective().getFullCode(),
                rrLossTable.getCurrency(),
                rrLossTable.getOriginalTarget().equals(RRLossTableType.CONFORMED.toString()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                rrLossTable.getRrLossTableId(),
                getFileExtension());
        File file = makeFullFile(getPrefixDirectory(), filename);
        xmlWriter.write(options, file);
        log.info("Write APS file: {}", file);
    }

}
