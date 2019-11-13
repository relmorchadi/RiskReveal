package com.scor.rr.request;

import com.scor.rr.enums.InuringOutputGrain;
import lombok.Data;

/**
 * Created by u004602 on 16/09/2019.
 */

public class InuringFinalNodeUpdateRequest {
    private int inuringFinalNodeId;
    private InuringOutputGrain inuringOutputGrain;

    public InuringFinalNodeUpdateRequest(int inuringFinalNodeId, InuringOutputGrain inuringOutputGrain) {
        this.inuringFinalNodeId = inuringFinalNodeId;
        this.inuringOutputGrain = inuringOutputGrain;
    }

    public InuringFinalNodeUpdateRequest() {
    }

    public int getInuringFinalNodeId() {
        return inuringFinalNodeId;
    }

    public InuringOutputGrain getInuringOutputGrain() {
        return inuringOutputGrain;
    }
}
