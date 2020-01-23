package com.scor.rr.service.batch.processor;

import com.scor.rr.domain.dto.CARDivisionDto;
import com.scor.rr.service.abstraction.ConfigurationService;
import com.scor.rr.service.batch.processor.rows.RLLocRow;
import com.scor.rr.service.state.FacParameters;
import com.scor.rr.service.state.TransformationPackage;
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

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ConfigurationService configurationService;

    @Value("#{jobParameters['carId']}")
    private String carId;

    @Override
    public RLLocRow process(RLLocRow item) throws Exception {
        if (forceCarId)
            item.setCarID(carId);

        Integer division = transformationPackage.getModelPortfolios().get(0).getDivision();
        if (transformationPackage.getModelPortfolios() != null && !transformationPackage.getModelPortfolios().isEmpty())
            item.setDivision(String.valueOf(division));
        // TODO : review later & currency
//        item.setAccuracyLevel(mappingHandler.getGeoResForCode(Integer.toString(item.getGeoResultionCode())));
        item.setCurrencyCode(
                configurationService.getDivisions(carId).stream().filter(div -> div.getDivisionNumber().equals(division))
                        .map(CARDivisionDto::getCurrency)
                        .findFirst().orElse("USD")
        );

        return item;
    }
}
