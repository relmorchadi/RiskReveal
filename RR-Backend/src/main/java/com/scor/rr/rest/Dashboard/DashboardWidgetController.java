package com.scor.rr.rest.Dashboard;


import com.scor.rr.domain.Response.ReferenceWidgetResponse;
import com.scor.rr.domain.Response.UserDashboardResponse;
import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.entities.Dashboard.UserDashboardWidget;
import com.scor.rr.domain.requests.DashboardWidgetCreationRequest;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.service.Dashboard.DashboardWidgetService;
import com.scor.rr.service.Dashboard.UserDashboardWidgetColumnsService;
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
    @Autowired
    private UserDashboardWidgetService userDashboardWidgetService;
    @Autowired
    private UserDashboardWidgetColumnsService userDashboardWidgetColumnsService;

    @GetMapping("getRefData")
    public List<ReferenceWidgetResponse> getReferenceData() throws RRException {
        return dashboardWidgetService.getReferenceWidgets();
    }

    @GetMapping("getWidgets")
    public List<UserWidgetResponse> getWidgets(@RequestParam long id) throws RRException {
        return userDashboardWidgetService.getDashboardWidget(id);
    }

    @PostMapping("create")
    public UserWidgetResponse createDashboardWidget(@RequestBody DashboardWidgetCreationRequest request) throws RRException {
        return userDashboardWidgetService.createDashboardWidget(request.getReferenceWidgetId(),request.getDashoardId(),request.getUserId());
    }

    @PutMapping("update")
    public ResponseEntity<?> updateDashboardWidget(@RequestBody UserDashboardWidget requet) throws RRException {
        userDashboardWidgetService.UpdateDashboardWidget(requet);
        return ResponseEntity.ok("updated");
    }

    @PostMapping("duplicate")
    public UserWidgetResponse duplicateDashboardWidget(@RequestParam long dashboardWidgetId) throws RRException {
        return userDashboardWidgetService.duplicateWidget(dashboardWidgetId);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteDashboardWidget(@RequestParam long dashboardWidgetId) throws RRException {
        userDashboardWidgetService.deleteDashboardWidget(dashboardWidgetId);
        return ResponseEntity.ok("deleted");
    }

    @DeleteMapping("deleteByRef")
    public ResponseEntity<?> deleteWidgetByRef(@RequestParam("dashboardId") long dashboardId,@RequestParam("refId") long referenceWidgetId) throws RRException{
        userDashboardWidgetService.deleteByRef(dashboardId,referenceWidgetId);
        return  ResponseEntity.ok("deleted");
    }


//    @GetMapping("getData")
//    public WidgetDataResponse  getDataForWidget(@RequestBody WidgetDataRequest request) throws RRException {
//        return userDashboardWidgetService.getWidgetData(request);
//    }

}
