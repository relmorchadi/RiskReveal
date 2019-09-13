package com.scor.rr.rest.cloning;

import com.scor.rr.domain.ScorPltHeaderEntity;
import com.scor.rr.service.cloning.CloningScorPltHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clone")
public class CloningScorPltHeaderRest {

    @Autowired
    CloningScorPltHeader cloningScorPltHeader;


    @GetMapping("clone-plt")
    public ScorPltHeaderEntity clonePltWithAdjustment(int pltHeaderEntityInitialId,String workspaceId) throws com.scor.rr.exceptions.RRException {
        return cloningScorPltHeader.clonePltWithAdjustment(pltHeaderEntityInitialId,workspaceId);
    }
}
