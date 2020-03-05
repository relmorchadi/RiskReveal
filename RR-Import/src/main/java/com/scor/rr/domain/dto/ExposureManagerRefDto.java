package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureManagerRefDto {

    private List<String> currencies;
    private List<String> financialPerspectives;
    private List<CARDivisionDto> divisions;
    private List<String> portfolios;
    private List<String> summariesDefinitions;
}
