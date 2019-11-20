package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentNodeProcessingEntity;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeProcessingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentNodeProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@RestController
@RequestMapping("api/nodeProcessing")
public class AdjustmentNodeProcessingRest {

    @Autowired
    AdjustmentNodeProcessingService adjustmentNodeProcessingService;


//    @PostMapping("input")
//    public AdjustmentNodeProcessingEntity saveByInputPlt(@RequestBody AdjustmentNodeProcessingRequest adjustmentNodeProcessingRequest) {
//        return adjustmentNodeProcessingService.saveByInputPlt(adjustmentNodeProcessingRequest);
//    }
//
//    @PostMapping("adjusted")
//    public AdjustmentNodeProcessingEntity saveByAdjustedPlt(@RequestBody AdjustmentParameterRequest parameterRequest) throws RRException {
//        return adjustmentNodeProcessingService.saveByAdjustedPlt(parameterRequest);
//    }

    @PostMapping("adjustNode")
    public AdjustmentNodeProcessingEntity adjustPLTPassingByNode(@RequestParam Integer nodeId) throws RRException {
        return adjustmentNodeProcessingService.adjustPLTPassingByNode(nodeId);
    }

    @PostMapping("adjustThread")
    public PltHeaderEntity adjustPLTsInThread(@RequestParam Integer threadId) throws RRException {
        return adjustmentNodeProcessingService.adjustPLTsInThread(threadId);

    }
}
