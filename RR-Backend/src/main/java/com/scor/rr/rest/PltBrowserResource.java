package com.scor.rr.rest;

import com.scor.rr.domain.dto.*;
import com.scor.rr.domain.dto.TargetBuild.AssignTagToPltsRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.service.PltBrowserService;
import com.scor.rr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("ids")
    public ResponseEntity<?> getIDs(PLTManagerIDsRequest request) { return ResponseEntity.ok(pltBrowserService.getIDs(request));}

    @GetMapping("columns")
    public ResponseEntity<?> getColumns() { return ResponseEntity.ok(pltBrowserService.getColumns());}

    @PostMapping("columns/width")
    public ResponseEntity<?> updateColumnWidth(@RequestBody UpdateColumnWidthRequest request) {
        this.pltBrowserService.updateColumnWidth(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("columns/sort")
    public ResponseEntity<?> updateColumnSort(@RequestBody UpdateColumnSortRequest request) {
        this.pltBrowserService.updateColumnSort(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("columns/sort/reset")
    public ResponseEntity<?> resetColumnSort(@RequestBody ResetColumnSortRequest request) {
        this.pltBrowserService.resetColumnSort(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("columns/filter")
    public ResponseEntity<?> updateColumnFilterCriteria(@RequestBody UpdateColumnFilterCriteriaRequest request) {
        this.pltBrowserService.updateColumnFilterCriteria(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("columns/filter/reset")
    public ResponseEntity<?> resetColumnFilterCriteria(@RequestBody ResetColumnFilterCriteriaRequest request) {
        this.pltBrowserService.resetColumnFilterCriteria(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("columns/orderandvisibility")
    public ResponseEntity<?> updateColumnOrderAndVisibility(@RequestBody UpdateColumnOrderAndVisibilityRequest request) {
        this.pltBrowserService.updateColumnOrderAndVisibility(request);
        return ResponseEntity.ok().build();
    }

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
