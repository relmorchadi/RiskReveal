package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentStateEntity;
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

    @GetMapping
    public List<AdjustmentStateEntity> findAll(){
        return adjustmentStateService.findAll();
    }
}
