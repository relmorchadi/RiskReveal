package com.scor.rr.domain.dto.grid;

import com.scor.rr.domain.enums.OrderType;
import lombok.Data;

import java.io.Serializable;

@Data
public class SortModel implements Serializable {
    private String colId;
    private OrderType sort;
}