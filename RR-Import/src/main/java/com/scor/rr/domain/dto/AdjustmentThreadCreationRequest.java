package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjustmentThreadCreationRequest {
    private Long pltPureId;
    private String createdBy;
    private boolean generateDefaultThread;
}