package com.scor.rr.service.batch;

import com.scor.rr.domain.ModelPortfolioEntity;
import com.scor.rr.domain.dto.CARDivisionDto;
import com.scor.rr.repository.RLModelDataSourceRepository;
import com.scor.rr.repository.RLPortfolioSelectionRepository;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.abstraction.ConfigurationService;
import com.scor.rr.service.state.TransformationPackage;
import com.scor.rr.util.EmbeddedQueries;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@StepScope
@Service
public class TivExtractor {

    @Autowired
    private RLPortfolioSelectionRepository rlPortfolioSelectionRepository;

    @Autowired
    private RLModelDataSourceRepository rlModelDataSourceRepository;

    @Autowired
    private RmsService rmsService;

    @Autowired
    private TransformationPackage transformationPackage;

    @Autowired
    private ConfigurationService configurationService;

    @Value("#{jobParameters['rlPortfolioSelectionIds']}")
    private String rlPortfolioSelectionIds;

    @Value("#{jobParameters['instanceId']}")
    private String instanceId;

    @Value("#{jobParameters['jobType']}")
    private String jobType;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['carId']}")
    private String carId;

    public RepeatStatus tivExtraction() {

        if (marketChannel.equalsIgnoreCase("Fac")) {
            List<ModelPortfolioEntity> modelPortfolios = transformationPackage.getModelPortfolios();

            for (ModelPortfolioEntity portfolioEntity : modelPortfolios) {

                List<Map<String, Object>> tivs;
                Integer division = transformationPackage.getModelPortfolios().get(0).getDivision();

                if (jobType.equals("PORTFOLIO"))
                    tivs = rmsService.getByQuery(EmbeddedQueries.TIV_QUERY.replaceAll(":edm:", portfolioEntity.getDataSourceName()), instanceId, portfolioEntity.getPortfolioName());
                else
                    tivs = rmsService.getByQuery(EmbeddedQueries.TIV_QUERY.replaceAll(":edm:", portfolioEntity.getDataSourceName()),
                            instanceId,
                            configurationService.getDivisions(carId).stream().filter(div -> div.getDivisionNumber().equals(division))
                                    .map(CARDivisionDto::getCurrency)
                                    .findFirst().orElse("USD")
                            , portfolioEntity.getPortfolioName());

//        for (Map<String, Object> tivMap : tivs) {
//            final ModellingResult result = modellingResultsByRegionPeril.get(tivMap.get("RegionPerilCode"));
//            if (result != null) {
//                result.setTivExposureValue((Double) tivMap.get("TotalTIV"));
//                result.setLocationCount((Integer) tivMap.get("LocCount"));
//            }
//        }
            }
        }
        return RepeatStatus.FINISHED;
    }
}