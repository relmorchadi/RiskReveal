package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.AnalysisEpCurves;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.enums.StatisticMetric;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class EpCurveWriter {

    @Value("${ihub.path}")
    private Path ihubPath;

    @Value("${ihub.prefix.directory}")
    private String prefixDirectory;

    public BinFile writeELTEPCurves(List<AnalysisEpCurves> metricToEPCurve, String filename) {
//        Map<StatisticMetric, List<AnalysisEpCurves>> metricToPLTEPCurve = new HashMap<>();

//        for (Map.Entry<StatisticMetric, List<AnalysisEpCurves>> entry : metricToEPCurve.entrySet()) {
//            // @TODO Review this logic With Viet
//            metricToPLTEPCurve.put(entry.getKey(), Lists.transform(entry.getValue(), e -> new AnalysisEpCurves(e)));
//        }

        return writePLTEPCurves(metricToEPCurve, filename);
    }

    private BinFile writePLTEPCurves(List<AnalysisEpCurves> metricToEPCurve, String filename) {
        File file = makeFullFile(prefixDirectory, filename);
        return writePLTEPCurves(metricToEPCurve, file);
    }

    private File makeFullFile(String prefixDirectory, String filename) {
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

    private BinFile writePLTEPCurves(List<AnalysisEpCurves> epCurves, File file) {

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            log.info("EPCurve file: {}", file);
            int size = 18 * epCurves.size();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            if (epCurves != null) {
                for (AnalysisEpCurves pltepCurve : epCurves) {
                    buffer.putInt(pltepCurve.getEpTypeCode());
                    buffer.putDouble(pltepCurve.getExeceedanceProb().doubleValue());
                    buffer.putDouble(pltepCurve.getLoss());
                }
            }

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

    private boolean closeDirectBuffer(final ByteBuffer buffer){
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
