package com.scor.rr.rest;

import com.scor.rr.domain.TargetBuild.PLTDetails.PLTDetailSummary;
import com.scor.rr.service.PLTDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/plt/detail")
public class PLTDetailResource {

    @Autowired
    PLTDetailService pltDetailService;

    @GetMapping("/summary")
    public PLTDetailSummary getSummaryDetail(@RequestParam Integer pltHeaderId) { return pltDetailService.getPLTDetailSummary(pltHeaderId); }
}
