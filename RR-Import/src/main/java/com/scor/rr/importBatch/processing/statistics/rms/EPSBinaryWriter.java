package com.scor.rr.importBatch.processing.statistics.rms;

import com.scor.rr.domain.entities.references.omega.BinFile;
import com.scor.rr.domain.entities.stat.RRSummaryStatistic;
import com.scor.rr.domain.utils.plt.SummaryStatistics;
import com.scor.rr.importBatch.processing.batch.BaseFileWriter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by U002629 on 22/09/2015.
 */
public class EPSBinaryWriter extends BaseFileWriter implements EPSWriter {
    private static final Logger log= LoggerFactory.getLogger(EPCurveBinaryWriter.class);

    public EPSBinaryWriter() {
    }

    public EPSBinaryWriter(String filePath, String fileExtension) {
        super(filePath, fileExtension);
    }

    @Override
    public void write(SummaryStatistics summaryStatistics, String type, String rp, String fp, String ccy, String model) {
        log.info("writing EPCurve files");
        Path eltOutDir = getDir();
        final Path fullPath = getIhubPath().resolve(eltOutDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();
        String eltFileName = getFileName(type, rp, fp, ccy, model, "EPS", getFileExtension());
        log.info("writing "+eltOutDir+"/"+eltFileName);

        FileChannel out=null;
        MappedByteBuffer buffer=null;
        try {
            out = new RandomAccessFile(new File(parent, eltFileName), "rw").getChannel();
            int size=24;
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putDouble(summaryStatistics.getPurePremium());
            buffer.putDouble(summaryStatistics.getStandardDeviation());
            buffer.putDouble(summaryStatistics.getCovariance());
            log.info("finished writing " + eltOutDir+"/"+eltFileName);
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }

    }
    private File makeFullFile(String filename) {
        String outDir = getPrefixDirectory();
        final Path fullPath = getIhubPath().resolve(outDir);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

    @Override
    public BinFile writeELTSummaryStatistics(RRSummaryStatistic summaryStatistics, String filename) { // ELTEPSummaryStatistic
        RRSummaryStatistic pltSummaryStatistic = new RRSummaryStatistic(summaryStatistics);
        return writePLTSummaryStatistics(pltSummaryStatistic, filename);
    }

    @Override
    public BinFile writePLTSummaryStatistics(RRSummaryStatistic summaryStatistics, String filename) {
        File file = makeFullFile(filename);
        return writePLTSummaryStatistics(summaryStatistics, file);
    }

    @Override
    public BinFile writePLTSummaryStatistics(RRSummaryStatistic summaryStatistics, File file) { // PLTSummaryStatistic
        FileChannel out=null;
        MappedByteBuffer buffer=null;
        try {
            log.info("Summary statistic file: {}", file);
            out = new RandomAccessFile(file, "rw").getChannel();
            int size=24;
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putDouble(summaryStatistics.getPurePremium());
            buffer.putDouble(summaryStatistics.getStandardDeviation());
            buffer.putDouble(summaryStatistics.getCov());
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
            return new BinFile(file);
        }
    }

}
