package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.AnalysisEpCurves;
import com.scor.rr.domain.dto.BinFile;
import com.scor.rr.domain.enums.StatisticMetric;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@StepScope
public class EpCurveWriter extends AbstractWriter{

    private Path ihubPath;

    @Value("${ihub.treaty.out.path}")
    private void setIhubPath(String path){
        this.ihubPath= Paths.get(path);
    }

    public BinFile writeELTEPCurves(List<AnalysisEpCurves> metricToEPCurve, String filename) {
//        Map<StatisticMetric, List<AnalysisEpCurves>> metricToPLTEPCurve = new HashMap<>();

//        for (Map.Entry<StatisticMetric, List<AnalysisEpCurves>> entry : metricToEPCurve.entrySet()) {
//            // @TODO Review this logic With Viet
//            metricToPLTEPCurve.put(entry.getKey(), Lists.transform(entry.getValue(), e -> new AnalysisEpCurves(e)));
//        }

        return writePLTEPCurves(metricToEPCurve, filename);
    }

    private BinFile writePLTEPCurves(List<AnalysisEpCurves> metricToEPCurve, String filename) {
        File file = makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename);
        return writePLTEPCurves(metricToEPCurve, file);
    }

    private BinFile writePLTEPCurves(List<AnalysisEpCurves> epCurves, File file) {

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            log.info("EPCurve file: {}", file);
            int size = 18 * epCurves.size();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            if (epCurves != null) {
                for (AnalysisEpCurves pltepCurve : epCurves) {
                    buffer.putShort( (short) pltepCurve.getEpTypeCode() );
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
