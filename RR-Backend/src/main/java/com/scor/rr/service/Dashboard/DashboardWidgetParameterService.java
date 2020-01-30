package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashboardWidgetParameter;
import com.scor.rr.exceptions.Dashboard.WidgetParameterNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.DashboardWidgetParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DashboardWidgetParameterService {

    @Autowired
    private DashboardWidgetParameterRepository dashboardWidgetParameterRepository;

    public List<DashboardWidgetParameter> getDashoardWidgetParameters(long dashboardWidgetId) throws RRException{

        List<DashboardWidgetParameter> listParam = dashboardWidgetParameterRepository.findByDashboardWidgetId(dashboardWidgetId);
        if(listParam == null || listParam.isEmpty()) throw new WidgetParameterNotFoundException(dashboardWidgetId);

        return listParam;

    }
}
