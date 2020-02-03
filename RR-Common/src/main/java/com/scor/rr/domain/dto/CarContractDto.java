package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarContractDto {

    private Long id;
    private String carId;
    private Long projectId;
    private String carName;
    private String carStatus;
    private String contractId;
    private String contractName;
    private Long uwYear;
    private String uwAnalysis;
    private String businessType;
    private String lob;
    private String client;
    private String subsidiary;
    private String sector;
    private List<CARDivisionDto> divisions;
}
