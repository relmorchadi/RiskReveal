package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadBranchingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadUpdateRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/thread")
public class AdjustmentThreadRest {

    @Autowired
    AdjustmentThreadService adjustmentThreadService;


    @PostMapping("create")
    public AdjustmentThreadEntity createNewAdjustmentThread(@RequestBody AdjustmentThreadCreationRequest request) throws RRException {
        return adjustmentThreadService.createNewAdjustmentThread(request);
    }

    @PostMapping("update")
    public AdjustmentThreadEntity updateAdjustmentThreadFinalPLT(@RequestBody AdjustmentThreadUpdateRequest request) throws RRException {
        return adjustmentThreadService.updateAdjustmentThreadFinalPLT(request);
    }

//    @PostMapping("clone")
//    public AdjustmentThreadEntity createNewAdjustmentThread(@RequestBody AdjustmentThreadCreationRequest request) throws RRException {
//        return adjustmentThreadService.createNewAdjustmentThread(request);
//    }

    @PostMapping("branch")
    public AdjustmentThreadEntity branchNewAdjustmentThread(@RequestBody AdjustmentThreadBranchingRequest request) throws RRException {
        return adjustmentThreadService.branchNewAdjustmentThread(request);
    }

    @GetMapping
    public AdjustmentThreadEntity findById(Integer id){
        return adjustmentThreadService.findOne(id);
    }


}
