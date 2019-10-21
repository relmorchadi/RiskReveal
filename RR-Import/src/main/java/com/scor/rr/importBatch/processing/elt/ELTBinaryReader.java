package com.scor.rr.importBatch.processing.elt;

import com.scor.rr.importBatch.processing.domain.loss.EventLoss;
import com.scor.rr.importBatch.processing.io.CSVWriter;
import com.scor.rr.importBatch.processing.io.CSVWriterImpl;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by u004119 on 09/06/2016.
 */
public class ELTBinaryReader {

    private static final Logger log = LoggerFactory.getLogger(ELTBinaryReader.class);

    String path = null;

    public void readELT(String filename) {
        int evCounter = 0;
        FileChannel fc = null;
        List<EventLoss> events = new ArrayList<>();
        try {
            fc = new FileInputStream(new File(path, filename)).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            //NOTE: JVM does not follow host endianness. By default is in big endian (motorola) whilst intel is little indian
            ib.order(ByteOrder.LITTLE_ENDIAN);
            while (ib.hasRemaining()) {
                evCounter++;
                if (evCounter == 18045) {
                    log.info("dddddddd");
                }
                final int eventId = ib.getInt();
                final float loss = ib.getFloat();
                final float exposure = ib.getFloat();
                final float stdDevC = ib.getFloat();
                final float stdDevUSq = ib.getFloat();
                final float rate = ib.getFloat();
                EventLoss eventLoss = new EventLoss(eventId, Double.valueOf(loss), Double.valueOf(stdDevC), Double.valueOf(stdDevUSq), Double.valueOf(exposure), Double.valueOf(rate), 0d);
                events.add(eventLoss);
            }
        } catch (IOException e) {
            log.error("Exception: ", e);
        } finally {
            //IOUtils.closeQuietly(fc);
        }

        testWriteELTValidation(events);

        if (writeELT(events)) {
            log.info("Greeat write");
        }

        log.info("Total events {}", evCounter);
    }

    // TEST VALIDATION
    private void testWriteELTValidation(List<EventLoss> eltData) {

        log.info("Write ELT for Chi");

        final String DELIMITER = ";";

        List<String> rows = new ArrayList<>();
        StringBuilder firstBuilder = new StringBuilder();
        firstBuilder.append("getEventId").append(DELIMITER)
                .append("getLoss").append(DELIMITER)
                .append("getExposureValue").append(DELIMITER)
                .append("getStdDevC").append(DELIMITER)
                .append("getStdDevUSq").append(DELIMITER)
                .append("getRate");
        rows.add(firstBuilder.toString());

        for (EventLoss eltLossnBetaFunction : eltData) {
            StringBuilder builder = new StringBuilder();
            builder.append(eltLossnBetaFunction.getEventId()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getMeanLoss()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getExposureValue()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getStdDevC()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getStdDevUSq()).append(DELIMITER)
                    .append(eltLossnBetaFunction.getFreq().floatValue());
            rows.add(builder.toString());

        }
        CSVWriter csvWriter = new CSVWriterImpl();
        csvWriter.writeCSV(rows, path, "ELT.csv");
    }

    private boolean writeELT(List<EventLoss> eltData) {
        log.info("Rewrite ELT for Chi");

        String name = "ELT_rewritten.csv";
        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(new File(path, name), "rw").getChannel();
            int size = 28 * eltData.size();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (EventLoss eltLossnBetaFunction : eltData) {
                buffer.putInt(eltLossnBetaFunction.getEventId().intValue());
                buffer.putFloat(eltLossnBetaFunction.getMeanLoss().floatValue());
                buffer.putFloat(eltLossnBetaFunction.getExposureValue().floatValue());
                buffer.putFloat(eltLossnBetaFunction.getStdDevC().floatValue());
                buffer.putFloat(eltLossnBetaFunction.getStdDevUSq().floatValue());
                buffer.putFloat(eltLossnBetaFunction.getFreq().floatValue());
            }
        } catch (IOException e) {
            log.error("Exception: ", e);
            return false;
        } finally {
            //IOUtils.closeQuietly(out);
            if (buffer != null) {
                closeDirectBuffer(buffer);
            }
        }
        return true;
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
