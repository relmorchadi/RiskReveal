package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentNodeEntity;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.adjustement.DefaultAdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/defaultadjustment")
public class DefaultAdjustmentRest {


    @Autowired
    DefaultAdjustmentService defaultAdjustmentService;

    @GetMapping("lookupdefaultadjustment")
    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltMarketChannel(Integer scorPltHeaderId) throws RRException {
        return defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(scorPltHeaderId);
    }

}
