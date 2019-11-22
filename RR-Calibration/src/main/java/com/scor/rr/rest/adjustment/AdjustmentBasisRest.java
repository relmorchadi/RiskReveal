package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentBasis;
import com.scor.rr.service.adjustement.AdjustmentBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/basis")
public class AdjustmentBasisRest {

    @Autowired
    AdjustmentBasisService adjustmentBasisService;

    @GetMapping
    public List<AdjustmentBasis> findAll(){
        return adjustmentBasisService.findAll();
    }

}
