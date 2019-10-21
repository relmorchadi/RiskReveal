package com.scor.rr.importBatch.processing.ylt;

import com.scor.rr.domain.entities.ihub.RRLossTable;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.rms.ProjectImportAssetLog;
import com.scor.rr.domain.enums.PLTStatus;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import com.scor.rr.importBatch.processing.domain.PLTData;
import com.scor.rr.importBatch.processing.domain.PLTLoss;
import com.scor.rr.importBatch.processing.domain.PLTPeriod;
import com.scor.rr.importBatch.processing.domain.PLTResult;
import com.scor.rr.importBatch.processing.treaty.PLTBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationBundle;
import com.scor.rr.importBatch.processing.treaty.TransformationPackage;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossData;
import com.scor.rr.importBatch.processing.treaty.loss.PLTLossPeriod;
import com.scor.rr.importBatch.processing.treaty.loss.ScorPLTLossDataHeader;
import com.scor.rr.importBatch.processing.treaty.services.TransformationUtils;
import com.scor.rr.importBatch.processing.ylt.meta.PLTPublishStatus;
import com.scor.rr.importBatch.processing.ylt.meta.XLTOT;
import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
import com.scor.rr.repository.rms.ProjectImportAssetLogRepository;
import com.scor.rr.utils.Step;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
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
import java.util.*;

/**
 * Created by U002629 on 05/03/2015.
 */
public class PLTBinaryWriter extends BaseFileWriter implements PLTWriter {

    private static final Logger log = LoggerFactory.getLogger(PLTBinaryWriter.class);
    private static boolean DBG = true;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ScorPLTHeaderRepository scorPLTHeaderRepository;

    @Autowired
    private ProjectImportAssetLogRepository projectImportAssetLogRepository;

    private PLTData pltData;

    public PLTBinaryWriter() {
    }

    public PLTBinaryWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public Boolean batchWrite() {
        log.debug("Starting PLTBinaryWriter");
        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            if (bundle.getPltBundles() == null) {
                if (DBG) log.error("ERROR in ELT {}, no PLTs found", bundle.getConformedRRLT().getRrLossTableId());
                continue;
            }
            for (PLTBundle pltBundle : bundle.getPltBundles()) {
                if (pltBundle.getPltError()) {
                    log.error("Ignoring one PLT having no data, conformed RRLT {}, RRLT {}, analysis id {}, analysis {}, profileKey {}, targetRap {}, PEQT {}",
                            bundle.getConformedRRLT().getRrLossTableId(), bundle.getSourceRRLT().getRrLossTableId(), bundle.getRrAnalysis().getAnalysisName(),
                            bundle.getRrAnalysis().getProfileKey(),
                            pltBundle.getHeader().getTargetRAP().getTargetRAPId(),
                            pltBundle.getHeader().getPeqtFile().getFileName());
                    // TODO - notify error
                    continue;
                }
//                writePLT(pltBundle.getHeader(), pltBundle.getLossDataHeader(), bundle.getConformedELTHeader());
                writePLT(pltBundle.getHeader100k(), pltBundle.getLossDataHeader100k(), bundle.getConformedRRLT());
            }
        }
        log.debug("PLTBinaryWriter completed");
        return true;
    }

    private void writePLT(ScorPLTHeader pltHeader, ScorPLTLossDataHeader pltLossDataHeader, RRLossTable rrImportedLossData) {
        String filename = makePLTFileName(
                pltHeader.getCreationDate(),
                pltHeader.getRegionPeril().getRegionPerilCode(),
                pltHeader.getFinancialPerspective().getCode(),
                pltHeader.getCurrency().getCode(),
                XLTOT.TARGET,
                pltHeader.getTargetRAP().getTargetRAPId(),
                pltHeader.getPltSimulationPeriods(),
                PLTPublishStatus.PURE,
                0, // pure PLT, no thread number
                pltHeader.getScorPLTHeaderId(),
                getFileExtension());
        File file = makeFullFile(getPrefixDirectory(), filename);
        writePLTLossDataV2(pltLossDataHeader.getSortedLossData(), file);
        pltHeader.setPltLossDataFile(new BinFile(file));
        if (DBG)
            log.info("ELT {}, PLT {}, {}-binary {}", rrImportedLossData.getRrLossTableId(), pltHeader.getScorPLTHeaderId(), pltLossDataHeader.getPltLossPeriods().size(), file);
    }

    @Override
    public Boolean writeHeader() {
        log.debug("Starting writeHeader");
        Date startDate = new Date();

        for (TransformationBundle bundle : transformationPackage.getBundles()) {
            // start new step (import progress) : step 14 WRITE_PLT_HEADER for this analysis in loop of many analysis :
            ProjectImportAssetLog projectImportAssetLogA = new ProjectImportAssetLog();
            //mongoDBSequence.nextSequenceId(projectImportAssetLogA);
            projectImportAssetLogA.setProjectId(getProjectId());
            projectImportAssetLogA.setStepId(14);
            projectImportAssetLogA.setStepName(Step.getStepNameFromStepIdForAnalysis(projectImportAssetLogA.getStepId()));
            projectImportAssetLogA.setStartDate(startDate);
            projectImportAssetLogA.setProjectImportLogId(bundle.getProjectImportLogAnalysisId());
            // --------------------------------------------------------------------

            if (bundle.getPltBundles() == null) {
                log.error("ERROR in RRLT {}, no PLTs found", bundle.getConformedRRLT().getRrLossTableId()); // tien copies from step 15, must ask Viet
                projectImportAssetLogA.getErrorMessages().add(String.format("ERROR in RRLT %s, no PLTs found", bundle.getConformedRRLT().getRrLossTableId()));
                Date endDate = new Date();
                projectImportAssetLogA.setEndDate(endDate);
                projectImportAssetLogRepository.save(projectImportAssetLogA);
                log.info("Finish import progress STEP 14 : WRITE_PLT_HEADER for analysis: {}", bundle.getSourceResult().getSourceResultId());
                continue;
            }
            for (PLTBundle pltBundle : bundle.getPltBundles()) {
                if (!pltBundle.getPltError()) {
                    persistHeader(pltBundle.getHeader100k());
                }
            }

            // finish step 14 WRITE_PLT_HEADER for one analysis in loop for of many analysis
            Date endDate = new Date();
            projectImportAssetLogA.setEndDate(endDate);
            projectImportAssetLogRepository.save(projectImportAssetLogA);
            log.info("Finish import progress STEP 14 : WRITE_PLT_HEADER for analysis: {}", bundle.getSourceResult().getSourceResultId());
        }
        log.debug("writeHeader completed");
        return true;
    }

    @Deprecated
    @Override
    public BinFile writeScorPLTLossData(ScorPLTLossDataHeader scorPLTLossDataHeader, String filename) {
        File file = makePLTFile(filename);
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        List<PLTLossPeriod> pltLossPeriods = scorPLTLossDataHeader.getPltLossPeriods();
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 0;
            for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
                if (pltLossPeriod.getPltLossDataByPeriods().size() > 0) {
                    size += (6 + (18 * pltLossPeriod.getPltLossDataByPeriods().size()));
                }
            }
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
                if (pltLossPeriod.getPltLossDataByPeriods().size() > 0) {
                    buffer.putInt(pltLossPeriod.getSimPeriod());
                    buffer.putShort((short) pltLossPeriod.getPltLossDataByPeriods().size());
                    for (PLTLossData pltLossData : pltLossPeriod.getPltLossDataByPeriods()) {
                        buffer.putInt(pltLossData.getEventId());
                        buffer.putShort((short) pltLossData.getSeq());
                        buffer.putLong(pltLossData.getEventDate());
                        buffer.putFloat((float) pltLossData.getLoss());
                    }
                }
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception: ", e);
            return null;
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
        if (DBG) log.info("PLT output: {}, {}", file.getParent(), file.getName());
        return new BinFile(file);
    }

    @Deprecated
    public BinFile testWriteScorPLTLossData(ScorPLTLossDataHeader scorPLTLossDataHeader, BinFile binFile) {
        File file = new File(binFile.getPath(), binFile.getFileName());
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        List<PLTLossPeriod> pltLossPeriods = scorPLTLossDataHeader.getPltLossPeriods();
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 0;
            for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
                if (pltLossPeriod.getPltLossDataByPeriods().size() > 0) {
                    size += (6 + (18 * pltLossPeriod.getPltLossDataByPeriods().size()));
                }
            }
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossPeriod pltLossPeriod : pltLossPeriods) {
                if (pltLossPeriod.getPltLossDataByPeriods().size() > 0) {
                    buffer.putInt(pltLossPeriod.getSimPeriod());
                    buffer.putShort((short) pltLossPeriod.getPltLossDataByPeriods().size());
                    for (PLTLossData pltLossData : pltLossPeriod.getPltLossDataByPeriods()) {
                        buffer.putInt(pltLossData.getEventId());
                        buffer.putShort((short) pltLossData.getSeq());
                        buffer.putLong(pltLossData.getEventDate());
                        buffer.putFloat((float) pltLossData.getLoss());
                    }
                }
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception: ", e);
            return null;
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
        if (DBG) log.info("PLT output: {}, {}", file.getParent(), file.getName());
        return new BinFile(file);
    }

    @Override
    public BinFile writeScorPLTLossData(List<PLTLossData> sortedList, String filename) {
        // TODO - removable?
        if (!TransformationUtils.isSortedReversely(sortedList)) {
            throw new IllegalStateException();
        }
        SortedMap<Integer, PLTLossPeriod> periodToLossPeriod = TransformationUtils.newReverseMap();
        for (PLTLossData pltLossData : sortedList) {
            int period = pltLossData.getSimPeriod();
            if (!periodToLossPeriod.containsKey(pltLossData.getSimPeriod())) {
                periodToLossPeriod.put(period, new PLTLossPeriod(period));
            }
            periodToLossPeriod.get(period).addPLTLossData(pltLossData);
        }

        ScorPLTLossDataHeader scorPLTLossDataHeader = new ScorPLTLossDataHeader();
        scorPLTLossDataHeader.setPltLossPeriods(new ArrayList<PLTLossPeriod>(periodToLossPeriod.values()));

        return null; // continue writing to v1
    }

    @Override
    public void writePLTLossDataV2(List<PLTLossData> list, File file) {
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        Comparator<PLTLossData> cmp = new Comparator<PLTLossData>() {
            @Override
            public int compare(PLTLossData o1, PLTLossData o2) {
                return new CompareToBuilder()
                        .append(o1.getSimPeriod(), o2.getSimPeriod())
                        .append(o1.getEventDate(), o2.getEventDate())
                        .append(o1.getEventId(), o2.getEventId())
                        .append(o1.getSeq(), o2.getSeq())
                        .toComparison();
            }
        };
        Collections.sort(list, cmp);
        try {
            int size = list.size() * 26;
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossData lossData : list) {
                buffer.putInt(lossData.getSimPeriod());
                buffer.putInt(lossData.getEventId());
                buffer.putLong(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception {}", e);
        } finally {
            // if (DBG) log.info("Writing PLT {}, nEvents = {}", file, list.size());
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
    }

    @Override
    public void writePLTLossDataNonRMS(List<PLTLossData> list, File file) {
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        file = makeFullFile(getPrefixDirectory(), file.getName());
        Comparator<PLTLossData> cmp = new Comparator<PLTLossData>() {
            @Override
            public int compare(PLTLossData o1, PLTLossData o2) {
                return new CompareToBuilder()
                        .append(o1.getSimPeriod(), o2.getSimPeriod())
                        .append(o1.getEventDate(), o2.getEventDate())
                        .append(o1.getEventId(), o2.getEventId())
                        .append(o1.getSeq(), o2.getSeq())
                        .toComparison();
            }
        };
        Collections.sort(list, cmp);
        try {
            int size = list.size() * 26;
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossData lossData : list) {
                buffer.putInt(lossData.getSimPeriod());
                buffer.putInt(lossData.getEventId());
                buffer.putLong(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception {}", e);
        } finally {
            // if (DBG) log.info("Writing PLT {}, nEvents = {}", file, list.size());
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
    }

    private File makePLTFile(String filename) {
        String outDir = getPrefixDirectory();
        final Path fullPath = getIhubPath().resolve(outDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            if (DBG) log.error("Exception: ", e);
            throw new RuntimeException("error creating paths " + fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

    public void writePLTLossDataV2ForXact(List<PLTLossData> list, BinFile binFile) {
        int eventCount = 0;
        if (list != null && !list.isEmpty() && !TransformationUtils.isSortedReversely(list)) {
            if (DBG) log.info("Input list is being sorted");
            TransformationUtils.sortReverse(list);
        }

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        File file = new File(binFile.getPath(), binFile.getFileName());
        try {
            //remplacer par 22
            int size = list.size() * 22;
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTLossData lossData : list) {
                buffer.putInt(lossData.getSimPeriod());
                buffer.putInt(lossData.getEventId());
                buffer.putLong(lossData.getEventDate());
                buffer.putShort((short) lossData.getSeq());
                //   buffer.putFloat((float) lossData.getMaxExposure());
                buffer.putFloat((float) lossData.getLoss());
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception {}", e);
        } finally {
            if (DBG) log.info("Writing PLTv2 {}, size = {}, eventCount = {}", file, list.size(), eventCount);
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
    }

    private void persistHeader(ScorPLTHeader scorPLTHeader) {
        if (scorPLTHeader.getPltLossDataFile() == null) {
            throw new IllegalStateException("Write binary files and assign their names to me first");
        }
        scorPLTHeader.setPltStatus(PLTStatus.ValidFull);
        scorPLTHeaderRepository.save(scorPLTHeader);
        if (DBG) log.info("Finish persisting ScorPLTHeader " + scorPLTHeader.getScorPLTHeaderId());
    }

    ///////// FAC ///////////

    public TransformationPackage getTransformationPackage() {
        return transformationPackage;
    }

    @Override
    public void writeLossData(PLTLoss lossData, Path pltOutDir, String regionPeril, String model, String identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Input identifier null");
        }
        if (DBG) log.info("writing PLT binary file for " + regionPeril);
        final Path fullPath = getIhubPath().resolve(pltOutDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            if (DBG) log.error("Exception: ", e);
            throw new RuntimeException("error creating paths " + fullPath, e);
        }
        final File parent = fullPath.toFile();

        String pltFile = getFileName("PLT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), model, "DAT", getFileExtension());
        pltFile = String.format(pltFile, identifier);
        if (DBG) log.info("writing " + pltOutDir + "/" + pltFile);
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(new File(parent, pltFile), "rw").getChannel();
            int size = 0;
            for (PLTPeriod pltPeriod : lossData.getPeriods()) {
                if (pltPeriod.getResults().size() > 0)
                    size += (6 + (18 * pltPeriod.getResults().size()));
            }
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (PLTPeriod pltPeriod : lossData.getPeriods()) {
                if (pltPeriod.getResults().size() > 0) {
                    buffer.putInt(pltPeriod.getPeriod());
                    buffer.putShort((short) pltPeriod.getResults().size());
                    for (PLTResult result : pltPeriod.getResults()) {
                        buffer.putInt(result.getEventId());
                        buffer.putShort((short) result.getSeq());
                        buffer.putLong(result.getEventDate());
                        buffer.putFloat(Double.valueOf(result.getLoss()).floatValue());
                    }
                }
            }
        } catch (IOException e) {
            if (DBG) log.error("Exception: ", e);
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
        if (DBG) log.info("finished writing " + pltOutDir + "/" + pltFile);
    }

    @Override
    public void writeLossData(PLTLoss lossData, String regionPeril, String model, String identifier) {
        writeLossData(lossData, getDir(), regionPeril, model, identifier);
    }

    public Boolean batchWriteFAC() {
        String identifier = null;
        Path pltOutDir = getDir();
        for (String regionPeril : pltData.getRegionPerils()) {
            PLTLoss lossData = pltData.getLossDataForRP(regionPeril);
            writeLossData(lossData, pltOutDir, regionPeril, "INTERNAL", identifier);
            if (DBG) log.info("finished writing PLT binary file for " + regionPeril);
            addMessage("PLT WRT", "PLT Files written OK for " + regionPeril);
        }
        return true;
    }

    public Boolean writeHeaderFAC() {
        Path pltOutDir = getDir();
        for (String regionPeril : pltData.getRegionPerils()) {
            if (DBG) log.info("writing PLT binary header for " + regionPeril);
            final Path fullPath = getIhubPath().resolve(pltOutDir);
            try {
                Files.createDirectories(fullPath);
            } catch (IOException e) {
                if (DBG) log.error("Exception: ", e);
                throw new RuntimeException("error creating paths " + fullPath, e);
            }
            final File parent = fullPath.toFile();
            PLTLoss lossData = pltData.getLossDataForRP(regionPeril);
            String pltFile = getFileName("PLT", regionPeril, lossData.getFinancialPerspective(), lossData.getCurrency(), "INTERNAL", "HDR", getFileExtension());
            if (DBG) log.info("writing " + pltOutDir + "/" + pltFile);
            FileChannel out = null;
            MappedByteBuffer buffer = null;
            try {
                out = new RandomAccessFile(new File(parent, pltFile), "rw").getChannel();
                int size = 0;
                buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
            } catch (IOException e) {
                if (DBG) log.error("Exception: ", e);
            } finally {
                //IOUtils.closeQuietly(out);
                if (buffer != null) {
                    closeDirectBuffer(buffer);
                }
            }
            addMessage("PLT WRT", "PLT Headers written OK for " + regionPeril);
        }
        return true;
    }

}
