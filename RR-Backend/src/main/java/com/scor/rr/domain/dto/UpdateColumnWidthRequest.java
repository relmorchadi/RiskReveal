package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class UpdateColumnWidthRequest {
    Long viewContextColumnId;
    String userCode;
    Integer width;
}
