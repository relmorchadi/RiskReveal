package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentThread;
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
    public AdjustmentThread createNewAdjustmentThread(@RequestBody AdjustmentThreadCreationRequest request) throws RRException {
        return adjustmentThreadService.createNewAdjustmentThread(request);
    }

    @PostMapping("lockUnlock")
    public AdjustmentThread updateAdjustmentThread(@RequestBody AdjustmentThreadUpdateRequest request) throws RRException {
        return adjustmentThreadService.lockUnlockAdjustmentThread(request);
    }

    @PostMapping("clone")
    public AdjustmentThread cloneThread(@RequestParam Integer threadId, @RequestParam boolean withAdjustment) throws RRException {
        return adjustmentThreadService.cloneThread(threadId, withAdjustment);
    }

    @PostMapping("branch")
    public AdjustmentThread branchNewAdjustmentThread(@RequestBody AdjustmentThreadBranchingRequest request) throws RRException {
        return adjustmentThreadService.branchNewAdjustmentThread(request);
    }
}
