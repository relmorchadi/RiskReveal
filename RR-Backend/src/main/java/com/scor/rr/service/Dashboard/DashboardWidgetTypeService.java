package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashBoardWidgetType;
import com.scor.rr.exceptions.Dashboard.DashBoardWidgetTypeNotValidException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.DashboardWidgetTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardWidgetTypeService {

    @Autowired
    private DashboardWidgetTypeRepository dashboardWidgetTypeRepository;

    public String getDashboardWidgetType(long id ) throws RRException {
        DashBoardWidgetType dashBoardWidgetType = dashboardWidgetTypeRepository.findByDashboardWidgetTypeId(id);
        if(dashBoardWidgetType == null) throw new DashBoardWidgetTypeNotValidException(id);

        return dashBoardWidgetType.getDashboardWidgetType();
    }
}
