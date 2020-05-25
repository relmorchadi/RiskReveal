package com.scor.rr.domain.Response;

import com.scor.rr.domain.entities.PLTManager.PLTManagerAll;
import javax.persistence.Tuple;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GridDataResponse {
    private List<Object> rows;
    private long lastRow;
    private List<String> secondaryColumnFields;

    public GridDataResponse(List<Object> data, long lastRow) {
        this.rows = data;
        this.lastRow = lastRow;
    }
}
