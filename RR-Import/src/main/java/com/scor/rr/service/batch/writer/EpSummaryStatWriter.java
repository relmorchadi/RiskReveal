package com.scor.rr.service.batch.writer;


import com.scor.rr.domain.AnalysisSummaryStats;
import com.scor.rr.domain.dto.BinFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;

@Slf4j
@Service
public class EpSummaryStatWriter {


    private Path ihubPath;

    @Value("${ihub.treaty.out.path}")
    private void setIhubPath(String path){
        this.ihubPath= Paths.get(path);
    }

    @Value("${ihub.prefix.directory}")
    private String prefixDirectory;

    public BinFile writePLTSummaryStatistics(AnalysisSummaryStats summaryStatisticHeaders, String filename) {
        File file = makeFullFile(filename);
        return writePLTSummaryStatistics(summaryStatisticHeaders, file);
    }

    private BinFile writePLTSummaryStatistics(AnalysisSummaryStats summaryStatistics, File file) { // PLTSummaryStatistic
        FileChannel out=null;
        MappedByteBuffer buffer=null;
        try {
            log.info("Summary statistic file: {}", file);
            out = new RandomAccessFile(file, "rw").getChannel();
            int size=24;
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

    private File makeFullFile(String filename) {
        final Path fullPath = ihubPath.resolve(prefixDirectory);
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

    protected boolean closeDirectBuffer(final ByteBuffer buffer){
        if(!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer)buffer;
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
