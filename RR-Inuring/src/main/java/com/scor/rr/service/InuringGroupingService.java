package com.scor.rr.service;

import com.scor.rr.JsonFormat.Plts;
import com.scor.rr.configuration.file.ChunkPLTFileReader;
import com.scor.rr.configuration.file.PLTFileReader;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringPltotFoundException;
import com.scor.rr.exceptions.inuring.InvalidNumberOfPLTS;
import com.scor.rr.exceptions.inuring.PltWithSameNameAlreadyExists;
import com.scor.rr.exceptions.inuring.PositivePLTNotFoundException;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.request.InuringGroupingRequest;
import io.swagger.models.auth.In;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class InuringGroupingService {

    @Autowired
    private PltHeaderRepository pltHeaderRepository;
    @Autowired
    private ChunkPLTFileReader pltFileReader;
    @Value(value = "${application.inuring.path}")
    private String path;

    public void groupPlts(InuringGroupingRequest request) throws RRException, NoSuchFieldException, IllegalAccessException, IOException {

        List<String> signs = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        List<Double> currencies = new ArrayList<>();
        if (request.getPlts() != null) {

            boolean positiveSignExists = false;
            int numberOfPLTS = request.getPlts().size();

            if(numberOfPLTS == 0 || numberOfPLTS == 1) throw new InvalidNumberOfPLTS(numberOfPLTS);

            for (Plts plt : request.getPlts()
                 ) {
                signs.add(plt.getSign());
                currencies.add(1.0); // need to get the right currency exchange rate
                if(plt.getSign().equals("+")){
                    positiveSignExists = true;
                }
                PltHeaderEntity pltHeaderEntity = pltHeaderRepository.findByPltHeaderId(plt.getPltId());
                if (pltHeaderEntity == null) throw new InuringPltotFoundException(plt.getPltId());

                File file = new File(plt.getPltFilePath());
                fileList.add(file);

            }
            if(!positiveSignExists) throw new PositivePLTNotFoundException();

            String targetFile = path +"GroupedPltsFolder"+"/";
            String folderPath = path +"temp"+request.getUserId()+"/" ;
            String pltName = request.getOutcomePltName();



            File checkFile = new File(targetFile + pltName + ".bin");
            if(checkFile.exists()) throw new PltWithSameNameAlreadyExists(pltName);


            boolean created = new File(folderPath).mkdir();
            boolean created1 = new File(targetFile).mkdir();



                Pair<Set<Integer>,Integer> par = pltFileReader.read(fileList,signs,currencies,folderPath);
                pltFileReader.createFinalPlt(pltName,par.getKey(),par.getValue(),folderPath,targetFile);
                pltFileReader.createFinalContributionMatrix(pltName+"LossContributionMatrix",par.getKey(),par.getValue()*((fileList.size()*8)+4),fileList.size(),"-Con.bin",folderPath,targetFile);
                pltFileReader.createFinalContributionMatrix(pltName+"ExpoContributionMatrix",par.getKey(),par.getValue()*((fileList.size()*8)+4),fileList.size(),"-ConMax.bin",folderPath,targetFile);

                System.gc();

            FileUtils.deleteDirectory(new File(folderPath));

        }

    }

}
