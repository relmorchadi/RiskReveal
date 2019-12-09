package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeUpdateRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.AdjustmentNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/node")
public class AdjustmentNodeRest {

    @Autowired
    AdjustmentNodeService adjustmentNodeService;

    @GetMapping("all")
    public List<AdjustmentNode> findAll() {
        return adjustmentNodeService.findAll();
    }

    @PostMapping("create")
    public AdjustmentNode createAdjustmentNode(@RequestBody AdjustmentNodeRequest request) throws RRException {
        return adjustmentNodeService.createAdjustmentNode(request);
    }

    @PostMapping("update")
    public AdjustmentNode updateAdjustmentNode(@RequestBody AdjustmentNodeUpdateRequest request) throws RRException {
        return adjustmentNodeService.updateAdjustmentNode(request);
    }

    @GetMapping("thread")
    public List<AdjustmentNode> findByThread(Integer threadId){
        return adjustmentNodeService.findByThread(threadId);
    }

    @PostMapping("delete")
    public void deleteNode(Integer id){
        adjustmentNodeService.deleteNode(id);
    }

}
