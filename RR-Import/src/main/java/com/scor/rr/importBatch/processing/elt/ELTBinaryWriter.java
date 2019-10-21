package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.domain.entities.ihub.LossDataFile;
import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.ihub.SourceResult;
import com.scor.rr.domain.entities.plt.RRAnalysis;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.entities.rms.RMSELTLoss;
import com.scor.rr.domain.enums.RRLossTableType;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.domain.ELTData;
import com.scor.rr.importBatch.processing.domain.ELTLoss;
import com.scor.rr.importBatch.processing.domain.loss.EventLoss;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.repository.ihub.RRLossTableRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.utils.Step;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

// old code ri
//import com.scor.almf.treaty.cdm.repository.elt.ELTHeaderRepository;

/**
 * Created by U002629 on 19/02/2015.
 */
public class ELTBinaryWriter extends BaseFileWriter implements ELTWriter {

    private static final Logger log = LoggerFactory.getLogger(ELTBinaryWriter.class);

    @Autowired
    private TransformationPackage transformationPackage;
    @Autowired
    private RRLossTableRepository rrLossTableRepository;
    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;
    @Autowired
    private RRLossTableRepository rrImportedLossDataRepository;

    private ELTData eltData;

    public ELTBinaryWriter() {
        super();
    }

    public ELTBinaryWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public Boolean batchWrite() {
        log.debug("Starting ELTBinaryWriter");

        Date startDate = new Date();

        for (TransformationBundle bundle : transformationPackage.getBundles()) {

            // start new step (import progress) : step 9 WRITE_ELT_BINARY for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(9);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------
//            log.info("Writing ELT binary file for analysis " + bundle.getRmsAnalysis().getAnalysisId());
//            writeELT(bundle.getSourceELTHeader(), bundle.getRmsAnalysisELT().getEltLosses(), importProgressA);
//            writeELT(bundle.getConformedELTHeader(), bundle.getConformedAnalysisELT().getEltLosses(), importProgressA);

            log.info("Writing RRLT binary file for analysis " + bundle.getRmsAnalysis().getAnalysisId());
            writeELT(bundle.getSourceResult(), bundle.getRrAnalysis(), bundle.getSourceRRLT(), bundle.getRmsAnalysisELT().getEltLosses(), projectImportAssetLogA);
            writeELT(bundle.getSourceResult(), bundle.getRrAnalysis(), bundle.getConformedRRLT(), bundle.getConformedAnalysisELT().getEltLosses(), projectImportAssetLogA);


            // finish step 9 WRITE_ELT_BINARY for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 9 : WRITE_ELT_BINARY for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("ELTBinaryWriter completed");
        return true;
    }

    private void writeELT(SourceResult sourceResult, RRAnalysis rrAnalysis, RRLossTable rrImportedLossData, List<RMSELTLoss> eltLossList, ProjectImportAssetLog projectImportAssetLogA) {
//        log.debug("Starting writeELT");
//        String filename = makeELTFileName(
//                eltHeader.getCreatedDate(),
//                eltHeader.getRegionPeril().getRegionPerilCode(),
//                eltHeader.getAnalysisFinancialPerspective().getFullCode(),
//                eltHeader.getCurrency(),
//                eltHeader.getEltType().equals(ELTHeaderType.SOURCE) ? XLTOT.ORIGINAL : XLTOT.TARGET,
//                eltHeader.getId(),
//                getFileExtension());

        log.debug("Starting write RRLT");
        String filename = makeELTFileName(
                rrAnalysis.getCreationDate(),
                rrAnalysis.getRegionPeril(),
                rrImportedLossData.getFinancialPerspective().getFullCode(),
                rrImportedLossData.getCurrency(),
                rrImportedLossData.getOriginalTarget().equals(RRLossTableType.SOURCE.toString()) ? XLTOT.ORIGINAL : XLTOT.TARGET,
                rrImportedLossData.getProjectId(),
                getFileExtension());
        log.debug("write ELT filename = {} elt lost list size = {}", filename, eltLossList.size());
        File file = writeELTFile(filename, rrImportedLossData, eltLossList, projectImportAssetLogA);
        if (file != null) {
            if (rrImportedLossData.getLossDataFile() == null)
                rrImportedLossData.setLossDataFile(new LossDataFile());

            rrImportedLossData.getLossDataFile().setFileName(new BinFile(file).getFileName()); // Set file name and persist ELTHeader
            rrImportedLossData.getLossDataFile().setFilePath(new BinFile(file).getPath()); // Set file name and persist ELTHeader
        }
        log.debug("writeELT completed");
    }

    private File writeELTFile(String filename, RRLossTable rrImportedLossData, List<RMSELTLoss> eltLossList, ProjectImportAssetLog projectImportAssetLogA) {
        log.debug("Starting write RRLT File");
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        File file = makeFullFile(getPrefixDirectory(), filename);
        int eventCount = 0;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 24 * eltLossList.size();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (RMSELTLoss rmseltLoss : eltLossList) {
                eventCount++;
                buffer.putInt(rmseltLoss.getEventId().intValue());
                buffer.putFloat(rmseltLoss.getLoss().floatValue());
                buffer.putFloat(rmseltLoss.getExposureValue().floatValue());
                buffer.putFloat(rmseltLoss.getStdDevC().floatValue());
                buffer.putFloat(rmseltLoss.getStdDevI().floatValue());
                buffer.putFloat(rmseltLoss.getRate().floatValue());
            }
        } catch (IOException e) {
            log.error("Error writing RRLT file {}, eventCount {}, exception {}", rrImportedLossData.getRrLossTableId(), eventCount, e);
            projectImportAssetLogA.getErrorMessages().add(String.format("Error writing RRLT file %s, eventCount %s, exception %s", rrImportedLossData.getRrLossTableId(), eventCount, e));
            return null;
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
        log.info("Finished writing RRLT file: {}, eventCount: {}", file, eventCount);
        // TransformationUtils.writeELT(eltLossesnBetaFunction, PublicDirectory.ROOT_ELT_PLT, filename + ".csv");
        log.debug("write RRLT File completed");
        return file;
    }

    //////// FAC ///////////

    @Override
    public Boolean writeHeader() {
        log.debug("Starting writeHeader");

        Date startDate = new Date();
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 10 WRITE_ELT_HEADER for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(10);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------

//            ELTHeader sourceELT = bundle.getSourceELTHeader();
//            eltHeaderRepository.save(sourceELT);
//
//            ELTHeader conformedELT = bundle.getConformedELTHeader();
//            eltHeaderRepository.save(conformedELT);

            RRLossTable sourceRRLT = bundle.getSourceRRLT();
            rrImportedLossDataRepository.save(sourceRRLT);

            RRLossTable conformedRRLT = bundle.getConformedRRLT();
            rrImportedLossDataRepository.save(conformedRRLT);

            log.info("Finish persisting ELT {}, conformed ELT {}", sourceRRLT.getRrLossTableId(), conformedRRLT.getRrLossTableId());

            // finis step 10 WRITE_ELT_HEADER for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 10 : WRITE_ELT_HEADER for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("writeHeader completed");
        return true;
    }

    public ELTData getEltData() {
        return eltData;
    }

    public void setEltData(ELTData eltData) {
        this.eltData = eltData;
    }

    public Boolean batchWriteFAC() {
        Path eltOutDir = getDir();
        for (String regionPeril : eltData.getRegionPerils()) {
            log.info("writing ELT binary file for " + regionPeril);
            final Path fullPath = getIhubPath().resolve(eltOutDir);
            try {
                Files.createDirectories(fullPath);
            } catch (IOException e) {
                log.error("Exception: ", e);
                throw new RuntimeException("error creating paths " + fullPath, e);
            }
            final File parent = fullPath.toFile();
            ELTLoss lossData = eltData.getLossDataForRp(regionPeril);
            String eltFileName = getFileName("ELT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), "MODEL", "DAT", getFileExtension());
            log.info("writing " + eltOutDir + "/" + eltFileName);
            FileChannel out = null;
            MappedByteBuffer buffer = null;
            try {

                out = new RandomAccessFile(new File(parent, eltFileName), "rw").getChannel();
                int size = 24 * lossData.getEventLosses().size();
                buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                for (EventLoss loss : lossData.getEventLosses()) {
                    buffer.putInt(loss.getEventId());
                    buffer.putFloat(loss.getMeanLoss().floatValue());
                    buffer.putFloat(loss.getExposureValue().floatValue());
                    buffer.putFloat(loss.getStdDevC().floatValue());
                    buffer.putFloat(loss.getStdDevU().floatValue());
                    buffer.putFloat(loss.getFreq().floatValue());
                }
                log.info("finished writing " + eltOutDir + "/" + eltFileName);
            } catch (IOException e) {
                log.error("Exception: ", e);
            } finally {
                //IOUtils.closeQuietly(out);
                if (buffer != null) {
                    closeDirectBuffer(buffer);
                }
            }

            addMessage("ELT WRITER", "ELTs written OK for " + regionPeril);
        }
        return true;
    }

    public Boolean writeHeaderFAC() {
        Path eltOutDir = getDir();
        for (String regionPeril : eltData.getRegionPerils()) {
            log.info("writing ELT binary header  for " + regionPeril);
            final Path fullPath = getIhubPath().resolve(eltOutDir);
            try {
                Files.createDirectories(fullPath);
            } catch (IOException e) {
                log.error("Exception: ", e);
                throw new RuntimeException("error creating paths " + fullPath, e);
            }
            final File parent = fullPath.toFile();
            ELTLoss lossData = eltData.getLossDataForRp(regionPeril);
            String headerFileName = getFileName("ELT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), "MODEL", "HDR", getFileExtension());
            log.info("writing " + eltOutDir + "/" + headerFileName);
            FileChannel out = null;
            MappedByteBuffer buffer = null;
            try {
                out = new RandomAccessFile(new File(parent, headerFileName), "rw").getChannel();
                int size = 0;
                buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
            } catch (IOException e) {
                log.error("Exception: ", e);
            } finally {
                //IOUtils.closeQuietly(out);
                if (buffer != null) {
                    closeDirectBuffer(buffer);
                }
            }
            addMessage("ELT WRITER", "ELT Headers written OK for " + regionPeril);
        }
        return true;
    }

}
