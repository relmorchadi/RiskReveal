package com.scor.rr.rest;

import com.scor.rr.domain.dto.TargetBuild.AssignTagToPltsRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTHeaderDeleteRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewRequest;
import com.scor.rr.domain.dto.TargetBuild.PLTManagerViewResponse;
import com.scor.rr.domain.dto.UpdateColumnWidthRequest;
import com.scor.rr.service.PltBrowserService;
import com.scor.rr.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/plt")
public class PltBrowserResource {

    @Autowired
    PltBrowserService pltBrowserService;

    @Autowired
    TagService tagService;

    @GetMapping
    public PLTManagerViewResponse getPLTHeaderView(PLTManagerViewRequest request) { return pltBrowserService.getPLTHeaderView(request); }

    @GetMapping("columns")
    public ResponseEntity<?> getColumns() { return ResponseEntity.ok(pltBrowserService.getColumns());}

    @PostMapping("columns/width")
    public ResponseEntity<?> updateColumnWidth(UpdateColumnWidthRequest request) {
        try {
            this.pltBrowserService.updateColumnWidth(request);
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            return ResponseEntity.badRequest()
                    .body("Couldnt update column width: " + e.getMessage());
        }
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
