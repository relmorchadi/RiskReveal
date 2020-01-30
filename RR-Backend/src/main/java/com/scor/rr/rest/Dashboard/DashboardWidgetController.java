package com.scor.rr.rest.Dashboard;


import com.scor.rr.domain.Response.ReferenceWidgetResponse;
import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.requests.DashboardWidgetCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.Dashboard.DashboardWidgetService;
import com.scor.rr.service.Dashboard.UserDashboardWidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/DashBoard/Widget")
public class DashboardWidgetController {

    @Autowired
    private DashboardWidgetService dashboardWidgetService;

    @GetMapping("getRefData")
    public List<ReferenceWidgetResponse> getReferenceData() throws RRException {
        return dashboardWidgetService.getReferenceWidgets();
    }

    @PostMapping("create")
    public UserWidgetResponse createDashboardWidget(@RequestBody DashboardWidgetCreationRequest request) throws RRException {
        return dashboardWidgetService.createDashboardWidget(request.getReferenceWidgetId(),request.getDashoardId(),request.getUserId());
    }

    @PutMapping("update")
    public ResponseEntity<?> updateDashboardWidget(@RequestBody UserWidgetResponse requet) throws RRException {
        dashboardWidgetService.UpdateDashboardWidget(requet);
        return ResponseEntity.ok("updated");
    }

    @PostMapping("duplicate")
    public UserWidgetResponse duplicateDashboardWidget(@RequestParam long dashboardWidgetId) throws RRException {
        return dashboardWidgetService.duplicateWidget(dashboardWidgetId);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteDashboardWidget(@RequestParam long dashboardWidgetId) throws RRException {
        dashboardWidgetService.deleteDashboardWidget(dashboardWidgetId);
        return ResponseEntity.ok("deleted");
    }

}
