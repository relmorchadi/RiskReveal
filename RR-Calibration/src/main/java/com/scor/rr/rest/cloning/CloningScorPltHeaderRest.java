package com.scor.rr.rest.cloning;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.dto.ClonePltsRequest;
import com.scor.rr.service.cloning.CloningScorPltHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clone")
public class CloningScorPltHeaderRest { // not in calibration now

    @Autowired
    CloningScorPltHeaderService cloningScorPltHeaderService;

    @PostMapping("clonePLT")
    public List<PltHeaderEntity> cloneDataPlts(@RequestBody ClonePltsRequest request) throws com.scor.rr.exceptions.RRException{
        System.out.println("[plt clone rest]: ********************");
        System.out.println(request);

        return this.cloningScorPltHeaderService.cloneDataPlts(request);
    }

//    @GetMapping("clonePLT")
//    public PltHeaderEntity clonePLTWithAdjustment(Long pltHeaderEntityInitialId, String workspaceId) throws com.scor.rr.exceptions.RRException {
//        return cloningScorPltHeaderService.clonePltWithAdjustment(pltHeaderEntityInitialId, workspaceId);
//    }
}
