package com.scor.rr.rest.ScopeOfCompleteness;

import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.ForeWriterExpectedScope;
import com.scor.rr.domain.entities.ScopeAndCompleteness.ProjectForewriterExpectedScope;
import com.scor.rr.domain.requests.DashboardWidgetCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.ScopeAndCompleteness.ProjectForewriterExpectedScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/DashBoard/ScopeFac")
public class ScopeOfCompletenessFac {

    @Autowired
    private ProjectForewriterExpectedScopeService projectForewriterExpectedScopeService;

    @PostMapping("create")
    public ResponseEntity<?> getTheExpectedScopeFac(@RequestParam("fileName") String fileName ) throws RRException {
         projectForewriterExpectedScopeService.storeTheExpectedScopeFac(fileName);
         return ResponseEntity.ok("Saved successfully");
    }
}
