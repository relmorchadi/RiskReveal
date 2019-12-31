package com.scor.rr.service.batch.processor;

import com.scor.rr.service.batch.processor.rows.RLLocRow;
import com.scor.rr.service.state.FacParameters;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@StepScope
public class LocItemProcessor implements ItemProcessor<RLLocRow, RLLocRow> {

    private boolean forceCarId = true;

    @Autowired
    private FacParameters facParameters;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['division']}")
    private String division;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Override
    public RLLocRow process(RLLocRow item) throws Exception {
        if (marketChannel.equalsIgnoreCase("Fac")) {
            if (forceCarId)
                item.setCarID(carId);

            item.setDivision(division);
            // TODO : review later & currency
//        item.setAccuracyLevel(mappingHandler.getGeoResForCode(Integer.toString(item.getGeoResultionCode())));
            item.setCurrencyCode("USD");

            return item;
        }
        return null;
    }
}
