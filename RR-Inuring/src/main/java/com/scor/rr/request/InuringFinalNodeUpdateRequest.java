package com.scor.rr.request;

import com.scor.rr.enums.InuringOutputGrain;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */
@Data
public class InuringFinalNodeUpdateRequest {
    private int inuringFinalNodeId;
    private InuringOutputGrain inuringOutputGrain;

}
