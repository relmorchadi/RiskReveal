package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisDetailedDto {

    private Long rlAnalysisId;
    private Long rlModelDataSourceId;
    private Long projectId;
    private Long rdmId;
    private String rdmName;
    private Long rlId;
    private String analysisName;
    private String analysisDescription;
    private String defaultGrain;
    private String analysisCurrency;
    private Number rlExchangeRate;
    private String region;
    private String peril;
    private String geoCode;
    private String rpCode;
    private String systemRegionPeril;
    private String subPeril;
    private String defaultOccurrenceBasis;
    private String regionName;
    private Long proportion = 100L;
    private Double unitMultiplier = 1.0d;
    private List<RLAnalysisToTargetRAPDto> referenceTargetRaps;
    private List<ExpectedFinancialPerspective> expectedFinancialPerspectives;
}
