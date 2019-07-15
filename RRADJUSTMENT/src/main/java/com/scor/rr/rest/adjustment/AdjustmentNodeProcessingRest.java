package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("api/nodeprocess")
public class AdjustmentNodeProcessingRest {

    @Autowired
    AdjustmentNodeProcessingService adjustmentNodeProcessingService;


    @PostMapping("input")
    public AdjustmentNodeProcessingEntity saveByInputPlt(@RequestBody AdjustmentNodeProcessingRequest adjustmentNodeProcessingRequest) {
        return adjustmentNodeProcessingService.saveByInputPlt(adjustmentNodeProcessingRequest);
    }

    @PostMapping("adjusted")
    public AdjustmentNodeProcessingEntity saveByAdjustedPlt(@RequestBody AdjustmentParameterRequest parameterRequest){
        return adjustmentNodeProcessingService.saveByAdjustedPlt(parameterRequest);
    }
}
