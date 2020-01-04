package com.scor.rr.rest;

import com.scor.rr.domain.dto.DashboardRequest;
import com.scor.rr.domain.entities.DashboardView;
import com.scor.rr.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dashboard")
public class DashboardResource {

    @Autowired
    DashboardService dashboardService;

    @PostMapping
    public Page<DashboardView> getAll(@RequestBody DashboardRequest request) { return this.dashboardService.getAll(request);}
}
