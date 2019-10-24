package com.scor.rr.request;

import com.scor.rr.enums.InuringElementType;
import lombok.Data;

@Data
public class InuringFilterCriteriaUpdateRequest {

    private int inuringFilterCriteriaId;

    private InuringElementType inuringObjectType;

    private int inuringObjectId;

    private String filterKey;

    private String filterValue;

    private boolean including;

}
