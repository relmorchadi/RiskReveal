package com.scor.rr.rest;

import com.scor.rr.domain.Response.DashboardDataResponse;
import com.scor.rr.domain.dto.DashboardRequest;
import com.scor.rr.domain.entities.DashboardView;
import com.scor.rr.service.Dashboard.UserDashboardWidgetService;
import com.scor.rr.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/dashboard")
public class DashboardResource {

    @Autowired
    private UserDashboardWidgetService userDashboardWidgetService;

    @PostMapping("getData")
    public DashboardDataResponse getAll(@RequestBody DashboardRequest request) {
        return this.userDashboardWidgetService.getDataForWidget(request);
    }
}
