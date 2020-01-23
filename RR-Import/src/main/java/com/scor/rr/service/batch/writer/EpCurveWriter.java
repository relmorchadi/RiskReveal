package com.scor.rr.service.batch.writer;

import com.scor.rr.domain.AnalysisEpCurves;
import com.scor.rr.configuration.file.BinFile;
import com.scor.rr.domain.dto.EPMetricPoint;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;


@Slf4j
@Service
@StepScope
public class EpCurveWriter extends AbstractWriter {

    private Path ihubPath;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Value("${ihub.treaty.out.path}")
    private void setIhubPath(String path) {
        this.ihubPath = Paths.get(path);
    }

    public BinFile writeELTEPCurves(List<AnalysisEpCurves> metricToEPCurve, String filename, Integer division) {

        return writeEPCurves(metricToEPCurve, filename, division);
    }

    private BinFile writeEPCurves(List<AnalysisEpCurves> metricToEPCurve, String filename, Integer division) {
        if (marketChannel.equalsIgnoreCase("Treaty")) {
            File file = makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename);
            return writeEPCurves(metricToEPCurve, file, null);
        } else {
            division = division != null ? division : Integer.valueOf(this.division);
            File file = makeFullFile(PathUtils.getPrefixDirectoryFac(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), division, carId, importSequence), filename);
            return writeEPCurves(metricToEPCurve, file, null);
        }
    }

    public BinFile writePLTEPCurves(List<EPMetricPoint> metricToEPCurve, String filename, StatisticMetric statisticMetric, Integer division) {

        if (marketChannel.equalsIgnoreCase("Treaty")) {
            File file = makeFullFile(PathUtils.getPrefixDirectory(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), Long.valueOf(projectId)), filename);
            return writeEPCurves(metricToEPCurve, file, statisticMetric);
        } else {
            File file = makeFullFile(PathUtils.getPrefixDirectoryFac(clientName, Long.valueOf(clientId), contractId, Integer.valueOf(uwYear), division, carId, importSequence), filename);
            return writeEPCurves(metricToEPCurve, file, statisticMetric);
        }


    }

    private BinFile writeEPCurves(List<?> epCurves, File file, StatisticMetric statisticMetric) {

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            log.info("EPCurve file: {}", file);
            if (epCurves != null) {
                int size = 18 * epCurves.size();
                buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
                buffer.order(ByteOrder.LITTLE_ENDIAN);

                for (Object pltepCurve : epCurves) {
                    if (pltepCurve instanceof AnalysisEpCurves) {
                        buffer.putShort((short) ((AnalysisEpCurves) pltepCurve).getEpTypeCode());
                        buffer.putDouble(((AnalysisEpCurves) pltepCurve).getExeceedanceProb().doubleValue());
                        buffer.putDouble(((AnalysisEpCurves) pltepCurve).getLoss());
                    }

                    if (pltepCurve instanceof EPMetricPoint) {
                        if (statisticMetric != null)
                            buffer.putShort((short) StatisticMetric.getFrom(statisticMetric));
                        buffer.putDouble(((EPMetricPoint) pltepCurve).getFrequency());
                        buffer.putDouble(((EPMetricPoint) pltepCurve).getLoss());
                    }
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

    private boolean closeDirectBuffer(final ByteBuffer buffer) {
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
