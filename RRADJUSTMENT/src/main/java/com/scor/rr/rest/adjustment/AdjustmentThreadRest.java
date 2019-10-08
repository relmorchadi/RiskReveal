package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentThreadEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/thread")
public class AdjustmentThreadRest {

    @Autowired
    AdjustmentThreadService adjustmentThreadService;


    @PostMapping("pure")
    public AdjustmentThreadEntity savePurePlt(@RequestBody AdjustmentThreadRequest request) throws RRException {
        return adjustmentThreadService.createNewAdjustmentThread(request);
    }

    @PostMapping("adjusted")
    public AdjustmentThreadEntity saveAdjustedPlt(@RequestBody AdjustmentThreadRequest request) throws RRException {
        return adjustmentThreadService.updateAdjustmentThreadFinalPLT(request);
    }

    @GetMapping
    public AdjustmentThreadEntity findById(Integer id){
        return adjustmentThreadService.findOne(id);
    }


}
