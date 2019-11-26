package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.*;

@Component
public class ChunkPLTFileReader {

    public Map<Integer, List<PLTLossData>> read(List<File> files) throws RRException {

        Map<Integer, List<PLTLossData>> map = new TreeMap<>();
        int i = 0;

        for (File file : files) {
            if (file == null || !file.exists())
                throw new PLTFileNotFoundException();
            if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
                throw new PLTFileExtNotSupportedException();
            try {
                byte[] fileBytes = Files.readAllBytes((file.toPath()));
                StringBuilder text = new StringBuilder();
                ByteBuffer ib = ByteBuffer.wrap(fileBytes);
                ib.order(ByteOrder.LITTLE_ENDIAN);
//                if (fileBytes.size() % 26 != 0)
//                    throw new PLTFileCorruptedException();

                int periodID = 0;
                while (ib.hasRemaining()) {
                    int period = ib.getInt();
                    int eventId = ib.getInt();
                    long eventDate = ib.getLong();
                    short seq = ib.getShort();
                    float exposure = ib.getFloat();
                    float loss = ib.getFloat();
                    PLTLossData lossData = new PLTLossData(period, eventId, eventDate, seq, exposure, loss);

                    if (period == periodID) {
                        List<PLTLossData> list = map.get(period);
                        Field a = PLTLossData.class.getDeclaredField("simPeriod");
                        list = ggg(list, list, lossData, a, i);
                        map.put(period, list);
                    } else {
                        periodID = period;
                        List<PLTLossData> pltLossDatas = new ArrayList<>();
                        pltLossDatas.add(lossData);
                        map.put(period, pltLossDatas);
                    }
                }
            } catch (IOException e) {
                throw new PLTFileCorruptedException();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            i = 5;
        }
        return map;
    }

    private List<PLTLossData> ggg(List<PLTLossData> parentTarget, List<PLTLossData> target, PLTLossData data, Field field, int i) throws IllegalAccessException, NoSuchFieldException {
        field.setAccessible(true);
        PLTLossData lastOgj = new PLTLossData();
        for (PLTLossData outcomePltLossData : target) {
            if (Double.valueOf(field.get(data).toString()).equals(Double.valueOf(field.get(outcomePltLossData).toString()))) {
                switch (field.getName()) {
                    case "simPeriod":
                        Field b = PLTLossData.class.getDeclaredField("eventId");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, b, i);
                    case "eventId":
                        Field c = PLTLossData.class.getDeclaredField("eventDate");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, c, i);
                    case "eventDate":
                        Field d = PLTLossData.class.getDeclaredField("seq");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, d, i);
                    default:
                        int index = parentTarget.indexOf(outcomePltLossData);

                        outcomePltLossData.setLoss(0);

                        parentTarget.set(index, outcomePltLossData);
                        return parentTarget;
                }
            } else if (Double.valueOf(field.get(data).toString()) < Double.valueOf(field.get(outcomePltLossData).toString())) {
                parentTarget.add(parentTarget.indexOf(outcomePltLossData), data);
                return parentTarget;
            }
            lastOgj = outcomePltLossData;
        }
        parentTarget.add(parentTarget.indexOf(lastOgj) + 1, data);
        return parentTarget;
    }

    private List<PLTLossData> getPLT(List<PLTLossData> target, int i, Object val, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        List<PLTLossData> newTarget = new ArrayList<>();
        newTarget.add(target.get(i));

        for (int j = i + 1; j < target.size(); j++) {
            if (field.get(target.get(j)).equals(val)) {
                newTarget.add(target.get(j));
            } else {
                return newTarget;
            }
        }
        return newTarget;
    }

    public void testRead(File file) throws IOException {
        byte[] fileBytes = Files.readAllBytes((file.toPath()));

        StringBuilder text = new StringBuilder();
        ByteBuffer ib = ByteBuffer.wrap(fileBytes);
        ib.order(ByteOrder.LITTLE_ENDIAN);
        List<PLTLossData> listTest = new ArrayList<>();

        while (ib.hasRemaining()) {
            int period = ib.getInt();
            int eventId = ib.getInt();
            long eventDate = ib.getLong();
            short seq = ib.getShort();
            float exposure = ib.getFloat();
            float loss = ib.getFloat();
            PLTLossData lossData = new PLTLossData(period, eventId, eventDate, seq, exposure, loss);
            listTest.add(lossData);
        }
        System.out.println("");


    }
}
