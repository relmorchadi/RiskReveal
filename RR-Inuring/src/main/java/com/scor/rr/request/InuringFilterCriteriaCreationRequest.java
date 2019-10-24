package com.scor.rr.request;

import com.scor.rr.enums.InuringElementType;
import lombok.Data;

@Data
public class InuringFilterCriteriaCreationRequest {

    private int inuringPackageId;

    private InuringElementType inuringObjectType;

    private int inuringObjectId;

    private String filterKey;

    private String filterValue;

    private boolean including;

}
