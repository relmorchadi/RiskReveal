package com.scor.rr.request;

import com.scor.rr.enums.InuringNodeType;
import lombok.Data;

@Data
public class InvalidateNodeRequest {

    private InuringNodeType inuringNodeType;
    private int nodeId;
}
