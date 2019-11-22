package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentCategoryEntity;
import com.scor.rr.service.adjustement.AdjustmentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class AdjustmentCategoryRest {


    @Autowired
    AdjustmentCategoryService adjustmentCategoryService;

    @GetMapping("all")
    public List<AdjustmentCategoryEntity> adjustmentCategoryEntities(){
        return adjustmentCategoryService.findAll();
    }

    @GetMapping("one")
    public AdjustmentCategoryEntity findOne(Integer id){
        return adjustmentCategoryService.findOne(id);
    }
}
