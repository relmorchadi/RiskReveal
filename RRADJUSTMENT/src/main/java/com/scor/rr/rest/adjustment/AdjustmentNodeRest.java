package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/node")
public class AdjustmentNodeRest {

    @Autowired
    AdjustmentNodeService adjustmentNodeService;


    @GetMapping
    public List<AdjustmentNodeEntity> findAll() {
        return adjustmentNodeService.findAll();
    }

    @PostMapping
    public AdjustmentNodeEntity save(@RequestBody AdjustmentNodeRequest request){
        return adjustmentNodeService.save(request);
    }

    @DeleteMapping
    public void deleteNode(Long id){
        adjustmentNodeService.delete(id);

    }

}
