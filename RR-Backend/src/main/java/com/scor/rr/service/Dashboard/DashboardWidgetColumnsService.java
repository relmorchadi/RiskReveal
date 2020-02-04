package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashboardWidgetColumns;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.DashboardWidgetColumnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardWidgetColumnsService {

    @Autowired
    private DashboardWidgetColumnsRepository dashboardWidgetColumnsRepository;

    public List<DashboardWidgetColumns> getDashboardWidgetColumns(long id) throws RRException {

        return dashboardWidgetColumnsRepository.findByDashboardWidgetId(id);

    }
 }
