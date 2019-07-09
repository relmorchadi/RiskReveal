package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentTypeEntity;
import com.scor.rr.service.adjustement.AdjustmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/type")
public class AdjustmentTypeRest {

    @Autowired
    AdjustmentTypeService adjustmentTypeService;


    @GetMapping
    public List<AdjustmentTypeEntity> findAll(){
        return adjustmentTypeService.findAll();
    }


}
