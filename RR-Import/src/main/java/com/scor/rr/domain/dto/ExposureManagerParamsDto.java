package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureManagerParamsDto {

    private Long projectId;
    private String summaryType;
    private String portfolioName;
    private Integer division;
    private String currency;
    private String financialPerspective;
    private Integer page;
    private Integer pageSize;
    private Boolean requestTotalRow;
    private String regionPerilFilter;
}
