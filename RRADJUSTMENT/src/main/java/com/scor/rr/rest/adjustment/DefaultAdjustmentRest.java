package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.AdjustmentNodeEntity;
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

    @GetMapping("entity")
    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltEntity(Integer scorPltHeaderId) {
      return defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltEntity(scorPltHeaderId);
    }

    @GetMapping("marketchannel")
    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltMarketChannel(Integer scorPltHeaderId) {
        return defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltMarketChannel(scorPltHeaderId);
    }

    @GetMapping("rptret")
    public List<AdjustmentNodeEntity> getDefaultAdjustmentNodeByPurePltRPAndTRAndET(Integer scorPltHeaderId) {
        return defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndET(scorPltHeaderId);
    }
}
