package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisToTargetRAPDto {

    private String targetRapCode;
    private boolean isDefault;
}
