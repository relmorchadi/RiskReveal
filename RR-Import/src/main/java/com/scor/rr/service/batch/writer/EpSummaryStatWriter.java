package com.scor.rr.service.batch.writer;


import com.scor.rr.domain.AnalysisSummaryStats;
import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.util.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;

@Slf4j
@Service
@StepScope
public class EpSummaryStatWriter extends AbstractWriter {

    private Path ihubPath;
    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;
    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("${ihub.treaty.out.path}")
    private void setIhubPath(String path) {
        this.ihubPath = Paths.get(path);
    }

    public BinFile writeELTSummaryStatistics(AnalysisSummaryStats summaryStatisticHeaders, String filename) {

        if (marketChannel.equalsIgnoreCase("Treaty")) {
            File file = makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename);
            return writeELTSummaryStatistics(summaryStatisticHeaders, file);
        } else {
            File file = makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename);
            return writeELTSummaryStatistics(summaryStatisticHeaders, file);
        }
    }

    private BinFile writeELTSummaryStatistics(AnalysisSummaryStats summaryStatistics, File file) {
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            log.info("Summary statistic file: {}", file);
            out = new RandomAccessFile(file, "rw").getChannel();
            int size = 24;
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.putDouble(summaryStatistics.getPurePremium());
            buffer.putDouble(summaryStatistics.getStdDev());
            buffer.putDouble(summaryStatistics.getCov());
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
            return new BinFile(file);
        }
    }

    protected boolean closeDirectBuffer(final ByteBuffer buffer) {
        if (!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer) buffer;
        return AccessController.doPrivileged(
                new PrivilegedAction<Object>() {
                    public Object run() {
                        try {
                            Cleaner cleaner = dbb.cleaner();
                            if (cleaner != null) cleaner.clean();
                            return null;
                        } catch (Exception e) {
                            return dbb;
                        }
                    }
                }
        ) == null;
    }
}
