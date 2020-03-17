package com.scor.rr.rest.ScopeOfCompleteness;

import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.ForeWriterExpectedScope;
import com.scor.rr.domain.entities.ScopeAndCompleteness.ProjectForewriterExpectedScope;
import com.scor.rr.domain.requests.DashboardWidgetCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.ScopeAndCompleteness.AccumulationPackageService;
import com.scor.rr.service.ScopeAndCompleteness.ProjectForewriterExpectedScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/DashBoard/ScopeFac")
public class ScopeOfCompletenessFac {

    @Autowired
    private ProjectForewriterExpectedScopeService projectForewriterExpectedScopeService;
    @Autowired
    private AccumulationPackageService accumulationPackageService;

    @PostMapping("create")
    public ResponseEntity<?> getTheExpectedScopeFac(@RequestParam("fileName") String fileName ) throws RRException {
         projectForewriterExpectedScopeService.storeTheExpectedScopeFac(fileName);
         return ResponseEntity.ok("Saved successfully");
    }

//    @GetMapping("getExpectedScope")
//    public List<Map<String,Object>> getDataForExpectedScope(@RequestParam("ProjectId") long projectId){
//        return accumulationPackageService.getExpectedScope(projectId);
//    }
}
