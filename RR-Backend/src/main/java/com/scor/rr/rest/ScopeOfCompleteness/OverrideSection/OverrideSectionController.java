package com.scor.rr.rest.ScopeOfCompleteness.OverrideSection;

import com.scor.rr.domain.Response.ScopeAndCompleteness.AccumulationPackageResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageOverrideSection;
import com.scor.rr.domain.requests.ScopeAndCompleteness.OverrideSectionRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.ScopeAndCompleteness.AccumulationPackageOverrideSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ScopeAndCompleteness/OverrideSection")
public class OverrideSectionController {

    @Autowired
    private AccumulationPackageOverrideSectionService accumulationPackageOverrideSectionService;

    @PostMapping("override")
    public AccumulationPackageResponse overrideSections(@RequestBody OverrideSectionRequest request ) throws RRException {
        return  accumulationPackageOverrideSectionService.overrideChosenSections(request);

    }

    @PostMapping("deleteOverride")
    public ResponseEntity<?> removeOverride(@RequestBody List<AccumulationPackageOverrideSection> overriddenSections){
        accumulationPackageOverrideSectionService.deleteOverride(overriddenSections);
        return ResponseEntity.ok("Deleted Successfully");
    }

}
