package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CARDivisionDto {

    private String caRequestId;
    private String carStatus;
    private Long projectId;
    private Long workspaceId;
    private String contractId;
    private Integer uwYear;
    private String currency;
    private Integer divisionNumber;
    private boolean IsPrincipalDivision;

}
