package com.scor.rr.domain.requests;

import com.scor.rr.domain.dto.grid.ColumnVO;
import com.scor.rr.domain.dto.grid.SortModel;
import lombok.Data;

import java.util.List;

@Data
public class GridDataRequest<T> {
    private int startRow, endRow, size;

    // row group columns
    private List<ColumnVO> rowGroupCols;

    // value columns
    private List<ColumnVO> valueCols;

    // pivot columns
    private List<ColumnVO> pivotCols;

    // true if pivot mode is one, otherwise false
    private boolean pivotMode;

    // what groups the user is viewing
    private List<String> groupKeys;

    // if filtering, what the filter model is
    private T filterModel;

    // if sorting, what the sort model is
    private List<SortModel> sortModel;

    private List<Long> pureIds;
}
