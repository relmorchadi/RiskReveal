package com.scor.rr.rest;

import com.scor.rr.domain.Response.DashboardChartResponse;
import com.scor.rr.domain.Response.DashboardDataResponse;
import com.scor.rr.domain.dto.DashboardRequest;
import com.scor.rr.domain.entities.DashboardView;
import com.scor.rr.service.Dashboard.UserDashboardWidgetService;
import com.scor.rr.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
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

//    @PostMapping("getChartData")
//    public List<DashboardChartResponse> getDataForChart(@RequestParam("From") long from ,@RequestParam("To")long  to){
//        Date f = new Date(from);
//        Date t = new Date(to);
//        return  this.userDashboardWidgetService.getChartData(f,t);
//    }
}
