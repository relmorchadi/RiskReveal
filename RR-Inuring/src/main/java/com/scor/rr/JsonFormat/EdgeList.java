package com.scor.rr.JsonFormat;

import com.scor.rr.entity.InuringFilterCriteria;
import com.scor.rr.enums.InuringFinancialPerspective;
import lombok.Data;

import java.util.List;

@Data
public class EdgeList {

    private int index;
    private long id;
    private String sign;
    private InuringFinancialPerspective perspective;
    private int sourceNodeIndex;
    private int targetNodeIndex;
    private List<InuringFilterCriteria> filterCriteriaList;
    private boolean outputAtLayerLevel;
}
