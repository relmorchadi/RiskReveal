package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentBasisEntity;
import com.scor.rr.service.adjustement.AdjustmentBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class AdjustmentBasisRest {

    @Autowired
    AdjustmentBasisService adjustmentBasisService;

    @GetMapping
    public List<AdjustmentBasisEntity> findAll(){
        return adjustmentBasisService.findAll();
    }

}
