package com.scor.rr.rest.ScopeOfCompleteness.AttachPLT;

import com.scor.rr.domain.Response.ScopeAndCompleteness.AccumulationPackageResponse;
import com.scor.rr.domain.Response.ScopeAndCompleteness.ScopeAndCompletenessResponse;
import com.scor.rr.domain.requests.ScopeAndCompleteness.AttachPLTRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.ScopeAndCompleteness.AccumulationPackageAttachedPLTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ScopeAndCompleteness/AttachPLT")
public class AttachPLTController {

    @Autowired
    private AccumulationPackageAttachedPLTService accumulationPackageAttachedPLTService;

    @PostMapping("attachPLTs")
    public AccumulationPackageResponse attachSelectedPLTs(@RequestBody AttachPLTRequest request) throws RRException {
        return accumulationPackageAttachedPLTService.attachSelectedPlts(request);
    }

    @DeleteMapping("deleteAttachements")
    public ResponseEntity<?> deleteAttachedPLTs(@RequestBody List<Long> pltIds){
        accumulationPackageAttachedPLTService.deleteSelectedAttachedPLTs(pltIds);
        return ResponseEntity.ok("Deleted successfully");
    }
}
