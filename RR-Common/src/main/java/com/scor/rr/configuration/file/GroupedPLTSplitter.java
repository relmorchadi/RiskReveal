package com.scor.rr.configuration.file;

import com.scor.rr.domain.dto.adjustement.loss.ContributionMatrice;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.ContributionMatrixNotFoundException;
import com.scor.rr.exceptions.pltfile.PLTFileCorruptedException;
import com.scor.rr.exceptions.pltfile.PLTFileExtNotSupportedException;
import com.scor.rr.exceptions.pltfile.PLTFileNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class GroupedPLTSplitter {

    @Autowired
    private FinalContributionMatrixReader finalContributionMatrixReader;
    @Autowired
    private FinalContributionMatrixWriter finalContributionMatrixWriter;



    public Map<Integer, List<PLTLossData>> contributionCorrecter(File groupedPlt, String lossContPath, String maxContPath, int numberOfPLTs,String separator) throws RRException {

        boolean changed = false;
        String tempFile = lossContPath +"temp"+ separator;
        String maxtempFile = maxContPath +"temp"+ separator;
        boolean created1 = new File(tempFile).mkdir();
        boolean created2 = new File(maxtempFile).mkdir();
        Map<Integer, List<PLTLossData>> map = new HashMap<>();
        List<ContributionMatrice> contributionMatrixMap = new ArrayList<>();
        List<ContributionMatrice> contributionMatrixMapMax = new ArrayList<>();
        List<PLTLossData> combinedPLT = new ArrayList<>();
        List<PLTLossData> pltToWrite = new ArrayList<>();

        if (groupedPlt == null || !groupedPlt.exists())
            throw new PLTFileNotFoundException();
        if (!"bin".equalsIgnoreCase(FilenameUtils.getExtension(groupedPlt.getName())))
            throw new PLTFileExtNotSupportedException();
        try {
            int phase = 0;
            int index = -1;
            PLTLossData moldLine = new PLTLossData();
            FileChannel fc = new FileInputStream(groupedPlt).getChannel();
            ByteBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            ib.order(ByteOrder.LITTLE_ENDIAN);
            if (fc.size() % 26 != 0) throw new PLTFileCorruptedException();

            while (ib.hasRemaining()) {
                index++;
                PLTLossData lossData = (PLTLossData) moldLine.clone();
                lossData.setSimPeriod(ib.getInt());
                lossData.setEventId(ib.getInt());
                lossData.setEventDate(ib.getLong());
                lossData.setSeq(ib.getShort());
                lossData.setMaxExposure(ib.getFloat());
                lossData.setLoss(ib.getFloat());
                int period = lossData.getSimPeriod();
                int rowPhase = (period / 1000) + 1;

                    combinedPLT.add(lossData);

                    if (phase != rowPhase) {
                        if(phase != 0){
                            finalContributionMatrixWriter.writeCorrectMatrice(contributionMatrixMap, new File(tempFile + (phase) + "-Con.bin"), contributionMatrixMap.size(), numberOfPLTs);
                            finalContributionMatrixWriter.writeCorrectMatrice(contributionMatrixMapMax, new File(maxtempFile + (phase) + "-ConMax.bin"), contributionMatrixMapMax.size(), numberOfPLTs);
                            contributionMatrixMap.clear();
                            contributionMatrixMapMax.clear();
                        }

                        File getChunkContributionMax = new File(maxContPath + (rowPhase) + "-ConMax.bin");
                        File getChunkContribution = new File(lossContPath + (rowPhase) + "-Con.bin");

                        if (getChunkContribution.exists() && getChunkContributionMax.exists()) {
                            contributionMatrixMap = finalContributionMatrixReader.read(getChunkContribution, numberOfPLTs);
                            contributionMatrixMapMax = finalContributionMatrixReader.read(getChunkContributionMax, numberOfPLTs);
                        } else {
                            throw new ContributionMatrixNotFoundException(rowPhase);
                        }
                        phase = rowPhase;
                        index = 0;
                    }
                    if (contributionMatrixMap != null && !contributionMatrixMap.isEmpty()) {
                        ContributionMatrice contLine = contributionMatrixMap.get(index);
                        String combinedKeyCont = contLine.getSimPeriod() + "-"+  contLine.getEventId() + "-"+ contLine.getEventDate() +"-"+ contLine.getSeq();
                        String combinedKeyPlt = lossData.getSimPeriod() + "-"+  lossData.getEventId() + "-"+ lossData.getEventDate() +"-"+ lossData.getSeq();
                        while (!combinedKeyCont.equals(combinedKeyPlt)) {
                            changed = true;
                            contributionMatrixMapMax.remove(index);
                            contributionMatrixMap.remove(index);
                            contLine = contributionMatrixMap.get(index);
                            combinedKeyCont = contLine.getSimPeriod() + "-"+  contLine.getEventId() + "-"+ contLine.getEventDate() +"-"+ contLine.getSeq();

                        }
                        ContributionMatrice maxContLine = contributionMatrixMapMax.get(index);
                        PLTLossData pltSplitted = (PLTLossData) moldLine.clone();
                        pltSplitted.setSimPeriod(lossData.getSimPeriod());
                        pltSplitted.setEventId(lossData.getEventId());
                        pltSplitted.setEventDate(lossData.getEventDate());
                        pltSplitted.setSeq(lossData.getSeq());
                        pltSplitted.setLoss(lossData.getLoss() * contLine.getContributions().get(0));
                        pltSplitted.setMaxExposure(lossData.getMaxExposure() * maxContLine.getContributions().get(0));

                        pltToWrite.add(pltSplitted);
                    }
                }

            finalContributionMatrixWriter.writeCorrectMatrice(contributionMatrixMap, new File(tempFile + (phase) + "-Con.bin"), contributionMatrixMap.size(), numberOfPLTs);
            finalContributionMatrixWriter.writeCorrectMatrice(contributionMatrixMapMax, new File(maxtempFile + (phase) + "-ConMax.bin"), contributionMatrixMapMax.size(), numberOfPLTs);
            contributionMatrixMap.clear();
            contributionMatrixMapMax.clear();



            map.put(1, combinedPLT);
            map.put(2, pltToWrite);

            FileUtils.closeDirectBuffer(ib);
            fc.close();
        } catch (IOException | CloneNotSupportedException e) {
            throw new PLTFileCorruptedException();
        } catch (PLTFileCorruptedException e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<PLTLossData> groupedPLTSplitter(List<PLTLossData> groupedPLT, String lossContPath, String maxContPath, int numberOfPLTs, int pltNumber) throws RRException, CloneNotSupportedException {

        List<ContributionMatrice> contributionMatrixMap = new ArrayList<>();
        List<ContributionMatrice> contributionMatrixMapMax = new ArrayList<>();
        List<PLTLossData> pltToWrite = new ArrayList<>();
        PLTLossData moldLine = new PLTLossData();
        int phase = 0;
        int index = -1;

        for (PLTLossData line : groupedPLT) {
            index++;

            int rowPhase = (line.getSimPeriod() / 1000) + 1;
            if (phase != rowPhase) {

                File getChunkContribution = new File(lossContPath + (rowPhase) + "-Con.bin");
                File getChunkContributionMax = new File(maxContPath + (rowPhase) + "-ConMax.bin");

                if (getChunkContribution.exists() && getChunkContributionMax.exists()) {
                    contributionMatrixMap = finalContributionMatrixReader.read(getChunkContribution, numberOfPLTs);
                    contributionMatrixMapMax = finalContributionMatrixReader.read(getChunkContributionMax, numberOfPLTs);
                } else {
                    throw new ContributionMatrixNotFoundException(rowPhase);
                }
                phase = rowPhase;
                index = 0;
            }

            ContributionMatrice contLine = contributionMatrixMap.get(index);
            ContributionMatrice maxContLine = contributionMatrixMapMax.get(index);
            PLTLossData pltSplitted = (PLTLossData) moldLine.clone();
            pltSplitted.setSimPeriod(line.getSimPeriod());
            pltSplitted.setEventId(line.getEventId());
            pltSplitted.setEventDate(line.getEventDate());
            pltSplitted.setSeq(line.getSeq());
            pltSplitted.setLoss(line.getLoss() * contLine.getContributions().get(pltNumber));
            pltSplitted.setMaxExposure(line.getMaxExposure() * maxContLine.getContributions().get(pltNumber));

            pltToWrite.add(pltSplitted);



        }
        return pltToWrite;


    }

}
