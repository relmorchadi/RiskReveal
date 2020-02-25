package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class UpdateColumnFilterCriteriaRequest {
    Long viewContextId;
    Long viewContextColumnId;
    String filterCriteria;
}
