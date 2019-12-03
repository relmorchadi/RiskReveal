package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PLTFileWriter pltFileWriter;

    public Map<Integer, List<PLTLossData>> read(List<File> files, List<String> signs, List<Double> currencies) throws RRException {

        Map<Integer, List<PLTLossData>> map = new TreeMap<>();
        List<Integer> listOfPeriods = new ArrayList<>();
        List<PLTLossData> simPeriodPlt = new ArrayList<>();
        List<Integer> periodIDs = new ArrayList<>();
        int idOfInsertion = 0;

        Map<Integer, List<List<Double>>> contributionMatrixMap = new TreeMap<>();
        List<List<Double>> contributionMatrix = new ArrayList<>();


        for (File file : files) {

            int i = files.indexOf(file);
            if (file == null || !file.exists())
                throw new PLTFileNotFoundException();
            if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
                throw new PLTFileExtNotSupportedException();
            try {
                FileChannel fc = new FileInputStream(file).getChannel();
                ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                ib.order(ByteOrder.LITTLE_ENDIAN);
                if (fc.size() % 26 != 0)
                    throw new PLTFileCorruptedException();

                int phase = 0;
                PLTLossData lossData = new PLTLossData();

                while (ib.hasRemaining()) {

                     lossData = buildPLTrow(lossData,ib);
                    int period = lossData.getSimPeriod();
                    int rowPhase = (period/ 1000) + 1;

                    if( phase == rowPhase ){
                        workTheMap(map, period, lossData, signs.get(i), currencies.get(i));
                    }
                    else{
                        if(phase != 0) {   writeMap(map, phase);}

                        phase = rowPhase;

                        File getChunkPlt = new File("C:\\GMB-FOLDER\\temp1\\" + (rowPhase) + ".bin");
                        if (getChunkPlt.exists()) {
                            map = readChunkedPltIntoMap(getChunkPlt);
                            workTheMap(map, period , lossData, signs.get(i), currencies.get(i));
                    }else{
                            List<PLTLossData> pltLossDatas = new ArrayList<>();
                            pltLossDatas.add(lossData);
                            map.put(period, pltLossDatas);
                        }
                    }
                }

                if (map.size() != 0) {
                    writeMap(map, phase);
                }
            } catch (IOException e) {
                throw new PLTFileCorruptedException();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private void workTheMap(Map<Integer,List<PLTLossData>> map , int period, PLTLossData lossData, String sign, Double exchangeRate) throws NoSuchFieldException, IllegalAccessException {
        if (map.containsKey(period)) {
            List<PLTLossData> list = map.get(period);
            Field a = PLTLossData.class.getDeclaredField("eventId");
            list = ggg(list, list, lossData, a, sign, exchangeRate);
            map.put(period, list);
        } else {
            List<PLTLossData> pltLossDatas = new ArrayList<>();
            pltLossDatas.add(lossData);
            map.put(period, pltLossDatas);
        }
    }


    private List<PLTLossData> ggg(List<PLTLossData> parentTarget, List<PLTLossData> target, PLTLossData data, Field field, String sign,Double currencyExchangeRate) throws IllegalAccessException, NoSuchFieldException {
        field.setAccessible(true);

        if(sign == "-") {
            data.setLoss(data.getLoss()*(-1)*currencyExchangeRate);
            data.setMaxExposure(data.getMaxExposure()*(-1)*currencyExchangeRate);
        }
        PLTLossData lastOgj = new PLTLossData();
        for (PLTLossData outcomePltLossData : target) {
            if (Double.valueOf(field.get(data).toString()).equals(Double.valueOf(field.get(outcomePltLossData).toString()))) {
                switch (field.getName()) {
                    case "simPeriod":
                        Field b = PLTLossData.class.getDeclaredField("eventId");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, b, "+", 1.0);
                    case "eventId":
                        Field c = PLTLossData.class.getDeclaredField("eventDate");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, c, "+",1.0);
                    case "eventDate":
                        Field d = PLTLossData.class.getDeclaredField("seq");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, d, "+",1.0);
                    default:
                        int index = parentTarget.indexOf(outcomePltLossData);


                        outcomePltLossData.setLoss(outcomePltLossData.getLoss() + data.getLoss());
                        outcomePltLossData.setMaxExposure(outcomePltLossData.getMaxExposure() + data.getMaxExposure());

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

    private void writeMap(Map<Integer, List<PLTLossData>> map, int phase) throws RRException {
        List<PLTLossData> writeList = new ArrayList<>();
        for (Map.Entry<Integer, List<PLTLossData>> entry : map.entrySet()) {
            writeList.addAll(entry.getValue());
        }
        pltFileWriter.write(writeList, new File("C:\\GMB-FOLDER\\temp1\\" + (phase) + ".bin"));
        map.clear();
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

    private Map<Integer, List<PLTLossData>> readChunkedPltIntoMap(File file) throws PLTFileCorruptedException, PLTFileNotFoundException, PLTFileExtNotSupportedException {
        Map<Integer, List<PLTLossData>> map = new TreeMap<>();

        if (file == null || !file.exists())
            throw new PLTFileNotFoundException();
        if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new PLTFileExtNotSupportedException();
        try {
            FileChannel fc = new FileInputStream(file).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            ib.order(ByteOrder.LITTLE_ENDIAN);
            if (fc.size() % 26 != 0)
                throw new PLTFileCorruptedException();

//            byte[] fileBytes = Files.readAllBytes((file.toPath()));
//            ByteBuffer ib = ByteBuffer.wrap(fileBytes);
//            ib.order(ByteOrder.LITTLE_ENDIAN);

            PLTLossData lossData = new PLTLossData();

            while (ib.hasRemaining()) {
                PLTLossData lossData1 = buildPLTrow(lossData,ib);
                int period = lossData1.getSimPeriod();

                if (map.containsKey(period)) {
                    List<PLTLossData> list = map.get(period);
                    list.add(lossData1);
                    map.put(period, list);
                } else {
                    List<PLTLossData> pltLossDatas = new ArrayList<>();
                    pltLossDatas.add(lossData1);
                    map.put(period, pltLossDatas);
                }
            }
        } catch (IOException e) {
            throw new PLTFileCorruptedException();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return map;

    }

    private PLTLossData buildPLTrow(PLTLossData pltLossData, ByteBuffer ib) throws CloneNotSupportedException {
        PLTLossData pltLossData1 = (PLTLossData) pltLossData.clone();
        pltLossData1.setSimPeriod(ib.getInt());
        pltLossData1.setEventId( ib.getInt());
        pltLossData1.setEventDate(ib.getLong());
        pltLossData1.setSeq(ib.getShort());
        pltLossData1.setMaxExposure( ib.getFloat());
        pltLossData1.setLoss(ib.getFloat());

        return pltLossData1;
    }
}


