package com.scor.rr.domain.Response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GridDataResponse {
    private List<Object> rows;
    private Integer lastRow;
    private List<String> secondaryColumnFields;

    public GridDataResponse(List<Object> data) {
        this.rows = data;
    }
}
