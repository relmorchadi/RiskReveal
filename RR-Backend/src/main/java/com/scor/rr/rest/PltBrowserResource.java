package com.scor.rr.rest;

import com.scor.rr.domain.dto.TargetBuild.AssignTagToPltsRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.domain.entities.PLTManagerView;
import com.scor.rr.repository.PLTManagerViewRepository;
import com.scor.rr.service.PltBrowserService;
import com.scor.rr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("api/plt")
public class PltBrowserResource {

    @Autowired
    PltBrowserService pltBrowserService;

    @Autowired
    PLTManagerViewRepository pltManagerViewRepository;

    @Autowired
    TagService tagService;

//    @GetMapping
//    public PLTManagerViewResponse getPLTHeaderView(PLTManagerViewRequest request) { return pltBrowserService.getPLTHeaderView(request); }

    @GetMapping
    public Set<PLTManagerView> getPLTHeaderView(String wsId, Integer uwYear) { return pltManagerViewRepository.findPLTs(wsId, uwYear); }

    @PostMapping("assign-user-tag")
    public Boolean assignUpdateUserTag(@RequestBody AssignTagToPltsRequest request) {
        return tagService.assignTagToPlts(request);
    }

    @PostMapping("delete")
    public Boolean deletePLT(@RequestBody PLTHeaderDeleteRequest request) {
        return pltBrowserService.deletePLTheader(request);
    }

    @PostMapping("restore")
    public Boolean deletePLT(@RequestBody List<Long> pltHeaderIds) {
        return pltBrowserService.restorePLTHeader(pltHeaderIds);
    }

}
