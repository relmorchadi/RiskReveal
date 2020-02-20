package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class UpdateColumnOrderAndVisibilityRequest {
    Long viewContextId;
    String columnsList;
}
