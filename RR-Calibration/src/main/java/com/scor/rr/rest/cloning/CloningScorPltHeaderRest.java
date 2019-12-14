package com.scor.rr.rest.cloning;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.service.cloning.CloningScorPltHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clone")
public class CloningScorPltHeaderRest { // not in calibration now

    @Autowired
    CloningScorPltHeaderService cloningScorPltHeaderService;

    @GetMapping("clonePLT")
    public PltHeaderEntity clonePLTWithAdjustment(Long pltHeaderEntityInitialId, String workspaceId) throws com.scor.rr.exceptions.RRException {
        return cloningScorPltHeaderService.clonePltWithAdjustment(pltHeaderEntityInitialId, workspaceId);
    }
}
