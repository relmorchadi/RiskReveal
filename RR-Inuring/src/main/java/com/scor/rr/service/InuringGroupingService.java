package com.scor.rr.service;

import com.scor.rr.JsonFormat.Plts;
import com.scor.rr.configuration.file.ChunkPLTFileReader;
import com.scor.rr.configuration.file.PLTFileReader;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringPltotFoundException;
import com.scor.rr.exceptions.inuring.InvalidNumberOfPLTS;
import com.scor.rr.exceptions.inuring.PositivePLTNotFoundException;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.request.InuringGroupingRequest;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class InuringGroupingService {

    @Autowired
    private PltHeaderRepository pltHeaderRepository;
    @Autowired
    private ChunkPLTFileReader pltFileReader;

    public void groupPlts(InuringGroupingRequest request) throws RRException, NoSuchFieldException, IllegalAccessException, IOException {

        Long first =  System.currentTimeMillis();
        if (request.getPlts() != null) {
            List<PLTLossData> outcomePlt = new ArrayList<>();

            boolean positiveSignExists = false;
            int numberOfPLTS = request.getPlts().size();

//            if(numberOfPLTS == 0 || numberOfPLTS == 1) throw new InvalidNumberOfPLTS(numberOfPLTS);
//            for (Plts checkingSign : request.getPlts()
//                 ) {
//                if(checkingSign.getSign().equals("+")){
//                    positiveSignExists = true;
//                }
//            }
//            if(!positiveSignExists) throw new PositivePLTNotFoundException();

//            for (int i=0; i<1;i++
//            ) {
//                PltHeaderEntity pltHeaderEntity = pltHeaderRepository.findByPltHeaderId(plt.getPltId());
//                if (pltHeaderEntity == null) throw new InuringPltotFoundException(plt.getPltId());

//                String sign = plt.getSign();
                String sign = "-";

                List<File> fileList = new ArrayList<>();
                File file = new File("C:\\GMB-FOLDER\\temp1\\1.bin");
                File file2 = new File("C:\\GMB-FOLDER\\processed\\plt6.bin");
                File file4 = new File("C:\\GMB-FOLDER\\processed\\plt6.bin");
                File file5 = new File("C:\\GMB-FOLDER\\processed\\plt6.bin");
                File file6 = new File("C:\\GMB-FOLDER\\processed\\plt6.bin");
                File file3 = new File("C:\\GMB-FOLDER\\processed\\plt6.bin");
                fileList.add(file);
//                fileList.add(file2);
//                fileList.add(file3);
//                fileList.add(file4);
//                fileList.add(file5);
//                fileList.add(file6);

//                pltFileReader.read(fileList);

                Map<Integer,List<PLTLossData>> result = pltFileReader.read(fileList);

//                for (PLTLossData pltLossData : result) {
//                    Field a = PLTLossData.class.getDeclaredField("simPeriod");
//
//                    if(sign.equals("-")){
//                        pltLossData.setLoss(pltLossData.getLoss()*(-1));
//                    }
//                    outcomePlt = ggg(outcomePlt, outcomePlt, pltLossData, a);
//                }

//            }
//
//            for (Map.Entry<Integer, List<PLTLossData>> entry : result.entrySet()) {
//                StringBuilder text = new StringBuilder();
//                System.out.println(entry.getKey() + ":");
//                for(PLTLossData loss: entry.getValue()){
//                    text.append("Loss:" + loss.getLoss());
//                }
//                System.out.println(text);
//            }
            Long second = System.currentTimeMillis();

//            for (PLTLossData loss : outcomePlt
//            ) {
//                text.append("simperoid:" + loss.getSimPeriod() );
//            }
            System.out.println("this function took: " + (second - first));
            System.out.println("this first took: " +  first);
            System.out.println("this second took: " + second );
//            System.out.println("map size" + result.size());
        }

    }

//    private List<PLTLossData> ggg(List<PLTLossData> parentTarget, List<PLTLossData> target, PLTLossData data, Field field) throws IllegalAccessException, NoSuchFieldException {
//        field.setAccessible(true);
//        PLTLossData lastOgj = new PLTLossData();
//        for (PLTLossData outcomePltLossData : target) {
//            if (Double.valueOf(field.get(data).toString()).equals(Double.valueOf(field.get(outcomePltLossData).toString()))) {
//                switch (field.getName()) {
//                    case "simPeriod":
//                        Field b = PLTLossData.class.getDeclaredField("eventId");
//                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, b);
//                    case "eventId":
//                        Field c = PLTLossData.class.getDeclaredField("eventDate");
//                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, c);
//                    case "eventDate":
//                        Field d = PLTLossData.class.getDeclaredField("seq");
//                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, d);
//                    default:
//                        int index = parentTarget.indexOf(outcomePltLossData);
//                        outcomePltLossData.setLoss(outcomePltLossData.getLoss() + data.getLoss());
//                        parentTarget.set(index, outcomePltLossData);
//                        return parentTarget;
//                }
//            } else if (Double.valueOf(field.get(data).toString()) < Double.valueOf(field.get(outcomePltLossData).toString())) {
//                parentTarget.add(parentTarget.indexOf(outcomePltLossData), data);
//                return parentTarget;
//            }
//            lastOgj = outcomePltLossData;
//        }
//        parentTarget.add(parentTarget.indexOf(lastOgj) + 1, data);
//        return parentTarget;
//    }
//
//    private List<PLTLossData> getPLT(List<PLTLossData> target, int i, Object val, Field field) throws IllegalAccessException {
//        field.setAccessible(true);
//        List<PLTLossData> newTarget = new ArrayList<>();
//        newTarget.add(target.get(i));
//
//        for (int j = i + 1; j < target.size(); j++) {
//            if (field.get(target.get(j)).equals(val) ) {
//                newTarget.add(target.get(j));
//            } else {
//                return newTarget;
//            }
//        }
//        return newTarget;
//    }
//     private void chunkPLT(File file) throws Exception {
//         File temp = File.createTempFile(file.getName(), null);
//         BufferedReader reader = null;
//         PrintStream writer = null;
//
//         try {
//             reader = new BufferedReader(new FileReader(file));
//             writer = new PrintStream(temp);
//
//             String line;
//             while ((line = reader.readLine())!=null) {
//                 // manipulate line
//                 writer.println(line);
//             }
//         }
//         finally {
//             if (writer!=null) writer.close();
//             if (reader!=null) reader.close();
//         }
//         if (!file.delete()) throw new Exception("Failed to remove " + file.getName());
//         if (!temp.renameTo(file)) throw new Exception("Failed to replace " + file.getName());
//     }

}
