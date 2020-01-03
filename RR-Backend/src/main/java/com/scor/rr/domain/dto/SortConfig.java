package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class SortConfig {
    String direction;
    String operator;
    Object value;
    String field;
}
