package com.scor.rr.rest;

import com.scor.rr.domain.dto.TargetBuild.AssignTagToPltsRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.service.PltBrowserService;
import com.scor.rr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/plt")
public class PltBrowserResource {

    @Autowired
    PltBrowserService pltBrowserService;

    @Autowired
    TagService tagService;

    @GetMapping
    public PLTManagerViewResponse getPLTHeaderView(PLTManagerViewRequest request) { return pltBrowserService.getPLTHeaderView(request); }


    @PostMapping("assign-user-tag")
    public Boolean assignUpdateUserTag(@RequestBody AssignTagToPltsRequest request) {
        return tagService.assignTagToPlts(request);
    }

    /*@PostMapping("delete")
    public Boolean deletePLT(@RequestBody PLTHeaderDeleteRequest request) {
        return pltBrowserService.deletePLTheader(request);
    }

    @PostMapping("restore")
    public Boolean deletePLT(@RequestBody List<Long> pltHeaderIds) {
        return pltBrowserService.restorePLTHeader(pltHeaderIds);
    }*/

}
