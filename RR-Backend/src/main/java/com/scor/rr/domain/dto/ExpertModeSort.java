package com.scor.rr.domain.dto;

import com.scor.rr.domain.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertModeSort {
    String columnName;
    OrderType order;
}
