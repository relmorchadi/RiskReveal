package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.RlEltLoss;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.domain.enums.XLTOT;
import com.scor.rr.domain.model.LossDataHeader;
import com.scor.rr.domain.riskReveal.RRAnalysis;
import com.scor.rr.repository.LossDataHeaderRepository;
import com.scor.rr.service.state.TransformationBundle;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.PathUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component(value = "eltWriter")
@StepScope
public class ELTWriter extends AbstractWriter {

    private static final Logger log = LoggerFactory.getLogger(ELTWriter.class);

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private LossDataHeaderRepository lossDataHeaderRepository;

    @Value("${ihub.treaty.out.path}")
    private String iHub;

    public RepeatStatus writeBinary() {

        log.debug("Starting ELTBinaryWriter");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            log.info("Writing RRLT binary file for analysis " + bundle.getRlAnalysis().getAnalysisId());

            writeELT(bundle.getRrAnalysis(), bundle.getSourceRRLT(), bundle.getRlAnalysisELT().getEltLosses());
            writeELT(bundle.getRrAnalysis(), bundle.getConformedRRLT(), bundle.getConformedRlAnalysisELT().getEltLosses());

            log.info("Finish import progress STEP 9 : WRITE_ELT_BINARY for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
        }
        log.debug("ELTBinaryWriter completed");
        return RepeatStatus.FINISHED;
    }

    private void writeELT(RRAnalysis rrAnalysis, LossDataHeader rrImportedLossData, List<RlEltLoss> eltLossList) {

        log.debug("Starting write RRLT");

        String filename = makeELTFileName(
                rrAnalysis.getCreationDate(),
                rrAnalysis.getRegionPeril(),
                rrAnalysis.getFinancialPerspective(),
                rrImportedLossData.getCurrency(),
                rrImportedLossData.getOriginalTarget().equals(RRLossTableType.SOURCE.getCode()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                rrImportedLossData.getLossDataHeaderId(),
                ".bin");

        log.debug("write ELT filename = {} elt lost list size = {}", filename, eltLossList.size());

        File file = writeELTFile(filename, rrImportedLossData, eltLossList);
        if (file != null) {

            rrImportedLossData.setLossDataFileName(new BinFile(file).getFileName()); // Set file name and persist ELTHeader
            rrImportedLossData.setLossDataFilePath(new BinFile(file).getPath()); // Set file name and persist ELTHeader
        }

        log.debug("writeELT completed");
    }

    private File writeELTFile(String filename, LossDataHeader rrImportedLossData, List<RlEltLoss> eltLossList) {
        log.debug("Starting write RRLT File");
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        Path iHubPath = Paths.get(iHub);
        File file = PathUtils.makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename, iHubPath);
        int eventCount = 0;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 24 * eltLossList.size();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (RlEltLoss rmseltLoss : eltLossList) {
                eventCount++;
                buffer.putInt(rmseltLoss.getEventId().intValue());
                buffer.putFloat((float) rmseltLoss.getLoss());
                buffer.putFloat((float) rmseltLoss.getExposureValue());
                buffer.putFloat((float) rmseltLoss.getStdDevC());
                buffer.putFloat((float) rmseltLoss.getStdDevI());
                buffer.putFloat((float) rmseltLoss.getRate());
            }
        } catch (IOException e) {
            log.error("Error writing RRLT file {}, eventCount {}, exception {}", rrImportedLossData.getLossDataHeaderId(), eventCount, e);
            return null;
        } finally {
            IOUtils.closeQuietly(out);
            if (buffer != null) {
                //closeDirectBuffer(buffer);
            }
        }
        log.info("Finished writing RRLT file: {}, eventCount: {}", file, eventCount);
        log.debug("write RRLT File completed");
        return file;
    }

    public RepeatStatus writeHeader() {

        log.debug("Starting writeHeader");

        for (TransformationBundle bundle : transformationPackage.getTransformationBundles()) {

            LossDataHeader sourceRRLT = bundle.getSourceRRLT();
            lossDataHeaderRepository.save(sourceRRLT);

            LossDataHeader conformedRRLT = bundle.getConformedRRLT();
            lossDataHeaderRepository.save(conformedRRLT);

            log.info("Finish persisting ELT {}, conformed ELT {}", sourceRRLT.getLossDataHeaderId(), conformedRRLT.getLossTableType());

            log.info("Finish import progress STEP 10 : WRITE_ELT_HEADER for analysis: {}", bundle.getSourceResult().getRlSourceResultId());
        }
        log.debug("writeHeader completed");
        return RepeatStatus.FINISHED;
    }
}
