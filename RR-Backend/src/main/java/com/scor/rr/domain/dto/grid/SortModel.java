package com.scor.rr.domain.dto.grid;

import lombok.Data;

import java.io.Serializable;

@Data
public class SortModel implements Serializable {
    private String colId;
    private String sort;
}