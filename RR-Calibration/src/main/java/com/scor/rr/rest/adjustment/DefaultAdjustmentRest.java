package com.scor.rr.rest.adjustment;

import com.scor.rr.domain.DefaultAdjustmentNode;
import com.scor.rr.domain.DefaultAdjustmentsInScopeView;
import com.scor.rr.domain.dto.DefaultAdjustmentsInScopeViewDTO;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.DefaultAdjustmentsInScopeRepository;
import com.scor.rr.service.adjustement.DefaultAdjustmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/defaultAdjustment")
public class DefaultAdjustmentRest {

    @Autowired
    DefaultAdjustmentsInScopeRepository defaultAdjustmentsInScopeRepository;

    @Autowired
    DefaultAdjustmentService defaultAdjustmentService;

    @GetMapping
    public ResponseEntity<?> getDefaultAdjustmentsInScope(@RequestParam String workspaceContextCode, @RequestParam int uwYear) {
        return this.defaultAdjustmentService.getDefaultAdjustmentsInScope(workspaceContextCode, uwYear);
    }

    @GetMapping("lookupDefaultAdjustment")
    public List<DefaultAdjustmentNode> getDefaultAdjustmentNodeByPurePltMarketChannel(int targetRapId,
                                                                                      int regionPerilId,
                                                                                      int marketChannelId,
                                                                                      String engineType,
                                                                                      int entityId) throws RRException {
        return defaultAdjustmentService.getDefaultAdjustmentNodeByPurePltRPAndTRAndETAndMC(targetRapId, regionPerilId, marketChannelId, engineType, entityId);
    }



}
