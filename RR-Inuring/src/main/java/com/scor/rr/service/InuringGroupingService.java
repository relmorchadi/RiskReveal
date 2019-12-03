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

        List<String> signs = new ArrayList<>();
        List<File> fileList = new ArrayList<>();
        List<Double> currencies = new ArrayList<>();
        Long first =  System.currentTimeMillis();
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



                Map<Integer,List<PLTLossData>> result = pltFileReader.read(fileList,signs,currencies);



            Long second = System.currentTimeMillis();


            System.out.println("this function took: " + (second - first));
            System.out.println("this first took: " +  first);
            System.out.println("this second took: " + second );
        }

    }

}
