package com.scor.rr.rest.ScopeOfCompleteness.AccumulationPackage;

import com.scor.rr.domain.Response.ScopeAndCompleteness.AccumulationPackageResponse;
import com.scor.rr.domain.Response.ScopeAndCompleteness.DropDownInfo;
import com.scor.rr.domain.Response.ScopeAndCompleteness.ScopeAndCompletenessResponse;
import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
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
@RequestMapping("api/ScopeAndCompleteness/AccumulationPackage")
public class AccumulationPackageController {

    @Autowired
    private AccumulationPackageService accumulationPackageService;


    @GetMapping("getScopeOnly")
    public List<ScopeAndCompletenessResponse> getScopeOnlyData(@RequestParam("workspaceId") String workspaceId,@RequestParam("uwyear") int uwyear,@RequestParam("projectId") long projectId){
        return accumulationPackageService.getScopeOnly(workspaceId,uwyear,projectId);
    }

    @GetMapping("getAccumulationPackage")
    public AccumulationPackageResponse getAccumulationPackage(@RequestParam("workspaceId") String workspaceId,
                                                              @RequestParam("uwyear") int uwyear,
                                                              @RequestParam("AccumulationPackageId") long accumulationPackageId,
                                                              @RequestParam("ProjectId") long projectId) throws RRException{
        return accumulationPackageService.getAccumulationPackageDetails(workspaceId,uwyear,accumulationPackageId,projectId);
    }

    @GetMapping("getDropDownInformation")
    public List<DropDownInfo> getDropDownInfo(@RequestParam("ProjectID") long projectId){
        return accumulationPackageService.getDropDownDetails(projectId);
    }



}
