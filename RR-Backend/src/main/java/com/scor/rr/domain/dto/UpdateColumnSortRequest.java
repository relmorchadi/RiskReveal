package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class UpdateColumnSortRequest {
    Long viewContextId;
    Long viewContextColumnId;
}
