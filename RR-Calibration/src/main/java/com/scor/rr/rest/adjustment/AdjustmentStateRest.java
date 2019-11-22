package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentState;
import com.scor.rr.service.adjustement.AdjustmentStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/state")
public class AdjustmentStateRest {

    @Autowired
    AdjustmentStateService adjustmentStateService;

    @GetMapping("all")
    public List<AdjustmentState> findAll(){
        return adjustmentStateService.findAll();
    }

    @GetMapping("one")
    public AdjustmentState findOne(Integer id) {
        return adjustmentStateService.findOne(id);
    }
}
