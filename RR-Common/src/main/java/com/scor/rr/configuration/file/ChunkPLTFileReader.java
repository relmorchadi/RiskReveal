package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import com.scor.rr.exceptions.pltfile.PLTFileWriteException;
import javafx.util.Pair;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ChunkPLTFileReader {
    @Autowired
    private BinaryPLTFileWriter pltFileWriter;
    @Autowired
    private ContributionMatrixReader contributionMatrixReader;
    @Autowired
    private ContributionMatrixWriter contributionMatrixWriter;
    @Autowired
    private ContributionMatrixCSVWriter contributionMatrixCSVWriter;
    @Autowired
    private FinalContributionMatrixWriter finalContributionMatrixWriter;


    public Pair<Set<Integer>, Integer> read(List<File> files, List<String> signs, List<Double> currencies, String folderPath, String lossContPath, String maxContPath, List<Long> listOfPltNames) throws RRException {

        Map<Integer, List<PLTLossData>> map = new TreeMap<>();
        Set<Integer> listOfPhases = new TreeSet<>();
        Map<Integer, List<List<Double>>> contributionMatrixMap = new TreeMap<>();
        Map<Integer, List<List<Double>>> contributionMatrixMapMax = new TreeMap<>();
        Map<Integer, List<List<Double>>> positiveSumsLossExpo = new TreeMap<>();
        boolean finalPhase = false;
        int size = 0;

        int idOfInsertion = -1;

        for (File file : files) {

            idOfInsertion++;
            if (idOfInsertion == files.size() - 1) {
                finalPhase = true;
            }
            int signValue = 1;
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
                PLTLossData lossData1 = new PLTLossData();


                if (signs.get(idOfInsertion).equals("-")) {
                    signValue = -1;
                }

                while (ib.hasRemaining()) {

                    PLTLossData lossData = (PLTLossData) lossData1.clone();
                    lossData.setSimPeriod(ib.getInt());
                    lossData.setEventId(ib.getInt());
                    lossData.setEventDate(ib.getLong());
                    lossData.setSeq(ib.getShort());
                    lossData.setMaxExposure(ib.getFloat());
                    lossData.setLoss(ib.getFloat());
                    int period = lossData.getSimPeriod();
                    int rowPhase = (period / 1000) + 1;
                    lossData.setLoss(lossData.getLoss() * signValue * currencies.get(idOfInsertion));
                    lossData.setMaxExposure(lossData.getMaxExposure() * signValue * currencies.get(idOfInsertion));

                    if (phase == rowPhase) {
                        workTheMap(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo, period, lossData, signValue, currencies.get(idOfInsertion), files.size(), idOfInsertion);
                    } else {
                        if (phase != 0) {
                            if (finalPhase) {
                                groupChunkPLT(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo);
                                int ret = writeMaps(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo, phase, files.size(), folderPath, lossContPath, maxContPath, true, listOfPltNames);
                                size = size + ret;
                                if (ret == 0) {
                                    listOfPhases.remove(phase);
                                }

                            } else {
                                writeMaps(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo, phase, files.size(), folderPath, lossContPath, maxContPath, false, listOfPltNames);
                                listOfPhases.add(phase);
                            }

                        }

                        phase = rowPhase;

                        File getChunkPlt = new File(folderPath + (rowPhase) + ".bin");
                        File getChunkContribution = new File(lossContPath + (rowPhase) + "-Con.bin");
                        File getChunkContributionMax = new File(maxContPath + (rowPhase) + "-ConMax.bin");
                        File getChunkPositiveLossExpo = new File(folderPath + (rowPhase) + "-PosLossExpo.bin");
                        if (getChunkPlt.exists() && getChunkContribution.exists() && getChunkContributionMax.exists() && getChunkPositiveLossExpo.exists()) {
                            map = readChunkedPltIntoMap(getChunkPlt);
                            contributionMatrixMap = contributionMatrixReader.read(getChunkContribution, ((files.size() * 4) + 4), files.size());
                            contributionMatrixMapMax = contributionMatrixReader.read(getChunkContributionMax, ((files.size() * 4) + 4), files.size());
                            positiveSumsLossExpo = contributionMatrixReader.read(getChunkPositiveLossExpo, 12, 2);
                            workTheMap(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo, period, lossData, signValue, currencies.get(idOfInsertion), files.size(), idOfInsertion);
                        } else {
                            List<PLTLossData> pltLossDatas = new ArrayList<>();
                            List<List<Double>> contributionPerPeriod = new ArrayList<>();
                            List<List<Double>> contributionPerPeriodMax = new ArrayList<>();
                            List<Double> contributionLine = new ArrayList<>(Collections.nCopies(files.size(), 0.0));
                            List<Double> contributionLineMax = new ArrayList<>(Collections.nCopies(files.size(), 0.0));


                            List<List<Double>> periodPositiveLossExpo = new ArrayList<>();
                            List<Double> positiveLossExpo = new ArrayList<>(Collections.nCopies(2, 0.0));
                            if (lossData.getLoss() >= 0) {
                                positiveLossExpo.set(0, lossData.getLoss());
                                positiveLossExpo.set(1, lossData.getMaxExposure());
                            }
                            periodPositiveLossExpo.add(positiveLossExpo);
                            positiveSumsLossExpo.put(period, periodPositiveLossExpo);

                            contributionLine.set(idOfInsertion, lossData.getLoss());
                            contributionPerPeriod.add(contributionLine);
                            contributionMatrixMap.put(period, contributionPerPeriod);

                            contributionLineMax.set(idOfInsertion, lossData.getMaxExposure());
                            contributionPerPeriodMax.add(contributionLineMax);
                            contributionMatrixMapMax.put(period, contributionPerPeriodMax);

                            pltLossDatas.add(lossData);
                            map.put(period, pltLossDatas);
                        }
                    }
                }
                FileUtils.closeDirectBuffer(ib);
                fc.close();

                if (map.size() != 0) {
                    if (finalPhase) {
                        groupChunkPLT(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo);
                        int ret = writeMaps(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo, phase, files.size(), folderPath, lossContPath, maxContPath, true, listOfPltNames);
                        size = size + ret;
                        if (ret == 0) {
                            listOfPhases.remove(phase);
                        }
                    } else {
                        writeMaps(map, contributionMatrixMap, contributionMatrixMapMax, positiveSumsLossExpo, phase, files.size(), folderPath, lossContPath, maxContPath, false, listOfPltNames);
                        listOfPhases.add(phase);
                    }
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
        return new Pair<>(listOfPhases, size);
    }

    private void workTheMap(Map<Integer, List<PLTLossData>> map, Map<Integer, List<List<Double>>> contributionMatrixMap, Map<Integer, List<List<Double>>> contributionMatrixMapMax, Map<Integer, List<List<Double>>> positiveSumsLossExpo, int period, PLTLossData lossData, int sign, Double exchangeRate, int size, int idOfInsertion) throws NoSuchFieldException, IllegalAccessException {
        if (map.containsKey(period)) {

            List<List<Double>> contributionPerPeriod = contributionMatrixMap.get(period);
            List<List<Double>> contributionPerPeriodMax = contributionMatrixMapMax.get(period);
            List<List<Double>> positiveLossExpo = positiveSumsLossExpo.get(period);
            List<PLTLossData> list = map.get(period);
            Field a = PLTLossData.class.getDeclaredField("eventId");
            list = ggg(list, list, lossData, a, contributionPerPeriod, contributionPerPeriodMax, positiveLossExpo, idOfInsertion, size).getKey();
            map.put(period, list);
            contributionMatrixMap.put(period, contributionPerPeriod);
            contributionMatrixMapMax.put(period, contributionPerPeriodMax);
            positiveSumsLossExpo.put(period, positiveLossExpo);
        } else {
            List<PLTLossData> pltLossDatas = new ArrayList<>();
            List<List<Double>> contributionPerPeriod = new ArrayList<>();
            List<List<Double>> contributionPerPeriodMax = new ArrayList<>();
            List<List<Double>> positiveLossMaxPeriod = new ArrayList<>();
            List<Double> contributionLine = new ArrayList<>(Collections.nCopies(size, 0.0));
            List<Double> contributionLineMax = new ArrayList<>(Collections.nCopies(size, 0.0));
            List<Double> positiveLossMax = new ArrayList<>(Collections.nCopies(2, 0.0));
            if (lossData.getLoss() >= 0) {
                positiveLossMax.set(0, lossData.getLoss());
                positiveLossMax.set(1, lossData.getMaxExposure());
            }
            positiveLossMaxPeriod.add(positiveLossMax);
            positiveSumsLossExpo.put(period, positiveLossMaxPeriod);
            pltLossDatas.add(lossData);
            contributionLine.set(idOfInsertion, lossData.getLoss());
            contributionLineMax.set(idOfInsertion, lossData.getMaxExposure());
            contributionPerPeriod.add(contributionLine);
            contributionPerPeriodMax.add(contributionLineMax);
            contributionMatrixMap.put(period, contributionPerPeriod);
            contributionMatrixMapMax.put(period, contributionPerPeriodMax);
            map.put(period, pltLossDatas);
        }
    }

    private Pair<List<PLTLossData>, List<List<Double>>> ggg(List<PLTLossData> parentTarget, List<PLTLossData> target, PLTLossData data, Field field, List<List<Double>> contributionPerPeriod, List<List<Double>> contributionPerPeriodMax, List<List<Double>> positiveLossExpo, int idOfInsertion, int size) throws IllegalAccessException, NoSuchFieldException {
        field.setAccessible(true);

        PLTLossData lastOgj = new PLTLossData();
        for (PLTLossData outcomePltLossData : target) {
            if (Double.valueOf(field.get(data).toString()).equals(Double.valueOf(field.get(outcomePltLossData).toString()))) {
                switch (field.getName()) {
                    case "simPeriod":
                        Field b = PLTLossData.class.getDeclaredField("eventId");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, b, contributionPerPeriod, contributionPerPeriodMax, positiveLossExpo, idOfInsertion, size);
                    case "eventId":
                        Field c = PLTLossData.class.getDeclaredField("eventDate");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, c, contributionPerPeriod, contributionPerPeriodMax, positiveLossExpo, idOfInsertion, size);
                    case "eventDate":
                        Field d = PLTLossData.class.getDeclaredField("seq");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, d, contributionPerPeriod, contributionPerPeriodMax, positiveLossExpo, idOfInsertion, size);
                    default:
                        int index = parentTarget.indexOf(outcomePltLossData);

                        if (data.getLoss() >= 0) {
                            List<Double> tempp = positiveLossExpo.get(index);
                            tempp.set(0, tempp.get(0) + data.getLoss());
                            tempp.set(1, tempp.get(1) + data.getMaxExposure());
                            positiveLossExpo.set(index, tempp);
                        }
                        List<Double> temp = contributionPerPeriod.get(index);
                        temp.set(idOfInsertion, data.getLoss());
                        contributionPerPeriod.set(index, temp);

                        List<Double> temp1 = contributionPerPeriodMax.get(index);
                        temp1.set(idOfInsertion, data.getMaxExposure());
                        contributionPerPeriodMax.set(index, temp1);


                        outcomePltLossData.setLoss(outcomePltLossData.getLoss() + data.getLoss());
                        outcomePltLossData.setMaxExposure(outcomePltLossData.getMaxExposure() + data.getMaxExposure());

                        parentTarget.set(index, outcomePltLossData);
                        return new Pair<>(parentTarget, contributionPerPeriod);
                }
            } else if (Double.valueOf(field.get(data).toString()) < Double.valueOf(field.get(outcomePltLossData).toString())) {

                List<Double> contributionLine = new ArrayList<>(Collections.nCopies(size, 0.0));
                List<Double> contributionLine1 = new ArrayList<>(Collections.nCopies(size, 0.0));

                List<Double> positiveLossMaxExpo = new ArrayList<>(Collections.nCopies(2, 0.0));
                if (data.getLoss() >= 0) {
                    positiveLossMaxExpo.set(0, data.getLoss());
                    positiveLossMaxExpo.set(1, data.getMaxExposure());
                }
                positiveLossExpo.add(parentTarget.indexOf(outcomePltLossData), positiveLossMaxExpo);

                contributionLine.set(idOfInsertion, data.getLoss());
                contributionLine1.set(idOfInsertion, data.getMaxExposure());

                contributionPerPeriod.add(parentTarget.indexOf(outcomePltLossData), contributionLine);
                contributionPerPeriodMax.add(parentTarget.indexOf(outcomePltLossData), contributionLine1);


                parentTarget.add(parentTarget.indexOf(outcomePltLossData), data);
                return new Pair<>(parentTarget, contributionPerPeriod);
            }
            lastOgj = outcomePltLossData;
        }
        List<Double> contributionLine = new ArrayList<>(Collections.nCopies(size, 0.0));
        List<Double> contributionLine1 = new ArrayList<>(Collections.nCopies(size, 0.0));

        List<Double> positiveLossMaxExpo = new ArrayList<>(Collections.nCopies(2, 0.0));
        if (data.getLoss() >= 0) {
            positiveLossMaxExpo.set(0, data.getLoss());
            positiveLossMaxExpo.set(1, data.getMaxExposure());
        }
        positiveLossExpo.add(parentTarget.indexOf(lastOgj) + 1, positiveLossMaxExpo);

        contributionLine.set(idOfInsertion, data.getLoss());
        contributionLine1.set(idOfInsertion, data.getMaxExposure());

        contributionPerPeriod.add(parentTarget.indexOf(lastOgj) + 1, contributionLine);
        contributionPerPeriodMax.add(parentTarget.indexOf(lastOgj) + 1, contributionLine1);

        parentTarget.add(parentTarget.indexOf(lastOgj) + 1, data);
        return new Pair<>(parentTarget, contributionPerPeriod);
    }

    private int writeMaps(Map<Integer, List<PLTLossData>> map, Map<Integer, List<List<Double>>> contMap, Map<Integer, List<List<Double>>> contMapMax, Map<Integer, List<List<Double>>> positiveLossExpoMap, int phase, int lineSize, String folderPath, String lossContPath, String maxContPath, boolean finalPhase, List<Long> listOfPltNames) throws RRException, IOException {
        List<PLTLossData> writeList = new ArrayList<>();
        int size = 0;

        for (Map.Entry<Integer, List<PLTLossData>> entry : map.entrySet()) {
            writeList.addAll(entry.getValue());
        }
        if (writeList.size() == 0) {
            try {

                Files.deleteIfExists(Paths.get(folderPath + (phase) + ".bin"));
                Files.deleteIfExists(Paths.get(folderPath + (phase) + "-PosLossExpo.bin"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            size = writeList.size();
            if (finalPhase) {

                Files.deleteIfExists(Paths.get(lossContPath + (phase) + "-Con.bin"));
                Files.deleteIfExists(Paths.get(maxContPath + (phase) + "-ConMax.bin"));
                Files.deleteIfExists(Paths.get(folderPath + (phase) + ".bin"));

                pltFileWriter.write(writeList, new File(folderPath + (phase) + ".bin"));
                finalContributionMatrixWriter.write(writeList, contMap, new File(lossContPath + (phase) + "-Con.bin"), writeList.size(), lineSize);
                finalContributionMatrixWriter.write(writeList, contMapMax, new File(maxContPath + (phase) + "-ConMax.bin"), writeList.size(), lineSize);
                contributionMatrixCSVWriter.write(writeList, contMap, new File(lossContPath + (phase) + "-Con.csv"), listOfPltNames);
                contributionMatrixCSVWriter.write(writeList, contMapMax, new File(maxContPath + (phase) + "-ConMax.csv"), listOfPltNames);
            }else{
                pltFileWriter.write(writeList, new File(folderPath + (phase) + ".bin"));
                contributionMatrixWriter.write(contMap, new File(lossContPath + (phase) + "-Con.bin"), writeList.size(), lineSize);
                contributionMatrixWriter.write(contMapMax, new File(maxContPath + (phase) + "-ConMax.bin"), writeList.size(), lineSize);
                contributionMatrixWriter.write(positiveLossExpoMap, new File(folderPath + (phase) + "-PosLossExpo.bin"), writeList.size(), 2);
            }
        }
        map.clear();
        contMap.clear();
        contMapMax.clear();
        positiveLossExpoMap.clear();
        return size;
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
        FileChannel fc = null;
        try {
            fc = new FileInputStream(file).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            ib.order(ByteOrder.LITTLE_ENDIAN);
            if (fc.size() % 26 != 0)
                throw new PLTFileCorruptedException();


            PLTLossData lossData = new PLTLossData();

            while (ib.hasRemaining()) {
                PLTLossData pltLossData1 = (PLTLossData) lossData.clone();
                pltLossData1.setSimPeriod(ib.getInt());
                pltLossData1.setEventId(ib.getInt());
                pltLossData1.setEventDate(ib.getLong());
                pltLossData1.setSeq(ib.getShort());
                pltLossData1.setMaxExposure(ib.getFloat());
                pltLossData1.setLoss(ib.getFloat());
                int period = pltLossData1.getSimPeriod();

                if (map.containsKey(period)) {
                    List<PLTLossData> list = map.get(period);
                    list.add(pltLossData1);
                    map.put(period, list);
                } else {
                    List<PLTLossData> pltLossDatas = new ArrayList<>();
                    pltLossDatas.add(pltLossData1);
                    map.put(period, pltLossDatas);
                }
            }
            FileUtils.closeDirectBuffer(ib);

        } catch (IOException e) {
            throw new PLTFileCorruptedException();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } finally {
            try {

                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return map;

    }


    private void groupChunkPLT(Map<Integer, List<PLTLossData>> map, Map<Integer, List<List<Double>>> contributionMatrixMap, Map<Integer, List<List<Double>>> contributionMatrixMapMax, Map<Integer, List<List<Double>>> positiveSumsLossExpo) {
        for (Map.Entry<Integer, List<PLTLossData>> entry : map.entrySet()) {

            int period = entry.getKey();
            List<PLTLossData> ttemp = map.get(period);
            List<List<Double>> temp = contributionMatrixMap.get(period);
            List<List<Double>> temp1 = contributionMatrixMapMax.get(period);
            List<List<Double>> temp2 = positiveSumsLossExpo.get(period);

            for (Iterator<PLTLossData> it = ttemp.iterator(); it.hasNext(); ) {
                PLTLossData plt = it.next();
                if (plt.getLoss() <= 0) {
                    int id = entry.getValue().indexOf(plt);

                    it.remove();
                    temp.remove(id);
                    temp1.remove(id);
                    temp2.remove(id);

                } else if (plt.getMaxExposure() <= 0) {
                    plt.setMaxExposure(plt.getLoss());
                }
            }

            int index = -1;
            for (List<Double> line : temp2
            ) {
                index++;

                List<Double> contLine = temp.get(index);
                List<Double> contLineMax = temp1.get(index);
                int a = 0;
                ;
                for (Double value : contLine) {
                    if (value < 0) {
                        value = 0.0;
                    } else {
                        value = value / line.get(0);
                    }
                    contLine.set(a, value);
                    a++;
                }
                a = 0;
                for (Double value : contLineMax) {
                    if (value < 0) {
                        value = 0.0;
                    } else {
                        value = value / line.get(1);
                    }

                    contLineMax.set(a, value);
                    a++;
                }

            }
            map.put(period, ttemp);
            contributionMatrixMap.put(period, temp);
            contributionMatrixMapMax.put(period, temp1);
            positiveSumsLossExpo.put(period, temp2);
        }

    }


    public void createFinalPlt(String pltName, Set<Integer> listOfPhases, int size, String folderPath, String targetFile) throws IOException, PLTFileWriteException {
        File file = new File(targetFile + pltName + ".bin");

        FileChannel out = null;
        MappedByteBuffer buffer = null;
        try {
            out = new RandomAccessFile(file, "rw").getChannel();
            buffer = out.map(FileChannel.MapMode.READ_WRITE, 0, size * 26);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            for (Integer phase : listOfPhases) {

                File filephase = new File(folderPath + phase + ".bin");
                try {
                    FileChannel fc = new FileInputStream(filephase).getChannel();
                    ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                    ib.order(ByteOrder.LITTLE_ENDIAN);
                    while (ib.hasRemaining()) {
                        buffer.putInt(ib.getInt());
                        buffer.putInt(ib.getInt());
                        buffer.putLong(ib.getLong());
                        buffer.putShort(ib.getShort());
                        buffer.putFloat(ib.getFloat());
                        buffer.putFloat(ib.getFloat());
                    }
                    FileUtils.closeDirectBuffer(ib);
                    fc.close();

                } catch (IOException e) {
                    throw new PLTFileWriteException(file.getPath());

                }
                Files.deleteIfExists(Paths.get(folderPath + (phase) + ".bin"));
                Files.deleteIfExists(Paths.get(folderPath + (phase) + "-PosLossExpo.bin"));

            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}


