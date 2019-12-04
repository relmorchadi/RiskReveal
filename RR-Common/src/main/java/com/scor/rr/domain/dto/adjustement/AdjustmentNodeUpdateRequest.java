package com.scor.rr.domain.dto.adjustement;

import com.scor.rr.domain.ReturnPeriodBandingAdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.domain.dto.adjustement.loss.PEATDataRequest;

import java.util.List;

/**
 * Created by U004602 on 21/11/2019.
 */
public class AdjustmentNodeUpdateRequest extends AdjustmentNodeRequest {
    private Integer adjustmentNodeId;
    private Integer adjustmentState;

    public AdjustmentNodeUpdateRequest(Integer adjustmentNodeId,
                                       Integer sequence,
                                       Boolean capped,
                                       Integer adjustmentBasis,
                                       Integer adjustmentType,
                                       Integer adjustmentState,
                                       Integer adjustmentThreadId,
                                       Double lmf, Double rpmf,
                                       PEATDataRequest peatData,
                                       List<ReturnPeriodBandingAdjustmentParameterRequest> adjustmentReturnPeriodBandings) {
        super(sequence, capped, adjustmentBasis, adjustmentType, adjustmentThreadId, lmf, rpmf, peatData, adjustmentReturnPeriodBandings);
        this.adjustmentNodeId = adjustmentNodeId;
        this.adjustmentState = adjustmentState;
    }

    public Integer getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Integer adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    public Integer getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(Integer adjustmentState) {
        this.adjustmentState = adjustmentState;
    }
}
