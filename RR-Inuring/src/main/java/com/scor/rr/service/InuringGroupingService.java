package com.scor.rr.service;

import com.scor.rr.JsonFormat.Plts;
import com.scor.rr.configuration.file.PLTFileReader;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringPltotFoundException;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.request.InuringGroupingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InuringGroupingService {

    @Autowired
    private PltHeaderRepository pltHeaderRepository;
    @Autowired
    private PLTFileReader pltFileReader;

    public void groupPlts(InuringGroupingRequest request) throws RRException, NoSuchFieldException, IllegalAccessException {
        if (request.getPlts() != null) {
            List<PLTLossData> outcomePlt = new ArrayList<>();

            for (Plts plt : request.getPlts()
            ) {
                PltHeaderEntity pltHeaderEntity = pltHeaderRepository.findByPltHeaderId(plt.getPltId());
                if (pltHeaderEntity == null) throw new InuringPltotFoundException(plt.getPltId());

                File file = new File("C:\\GMB-FOLDER\\processed\\test_RR4.bin");
                List<PLTLossData> result = pltFileReader.read(file);

                for (PLTLossData pltLossData : result) {
                    Field a = PLTLossData.class.getDeclaredField("simPeriod");
                    outcomePlt = ggg(outcomePlt, outcomePlt, pltLossData, a);
                }

            }
            for (PLTLossData loss : outcomePlt
            ) {
                System.out.println("simperoid:" + loss.getSimPeriod() + " eveId:" + loss.getEventId() + " date: " + loss.getEventDate() + " loss: " + loss.getLoss());
            }
        }

    }

    private List<PLTLossData> ggg(List<PLTLossData> parentTarget, List<PLTLossData> target, PLTLossData data, Field field) throws IllegalAccessException, NoSuchFieldException {
        field.setAccessible(true);
        PLTLossData lastOgj = new PLTLossData();
        for (PLTLossData outcomePltLossData : target) {
            if (Double.valueOf(field.get(data).toString()).equals(Double.valueOf(field.get(outcomePltLossData).toString()))) {
                switch (field.getName()) {
                    case "simPeriod":
                        Field b = PLTLossData.class.getDeclaredField("eventId");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, b);
                    case "eventId":
                        Field c = PLTLossData.class.getDeclaredField("eventDate");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, c);
                    case "eventDate":
                        Field d = PLTLossData.class.getDeclaredField("seq");
                        return ggg(parentTarget, getPLT(target, target.indexOf(outcomePltLossData), field.get(outcomePltLossData), field), data, d);
                    default:
                        int index = parentTarget.indexOf(outcomePltLossData);
                        outcomePltLossData.setLoss(outcomePltLossData.getLoss() * 2);
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
}
