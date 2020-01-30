package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.Response.ReferenceWidgetResponse;
import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.entities.Dashboard.*;
import com.scor.rr.exceptions.Dashboard.DashboardWidgetNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.DashboardWidgetRepository;
import com.scor.rr.repository.Dashboard.DashboardWidgetTypeRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetColumnsRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DashboardWidgetService {

    @Autowired
    private DashboardWidgetRepository dashboardWidgetRepository;

    public List<ReferenceWidgetResponse> getReferenceWidgets(){
        List<ReferenceWidgetResponse> listRef = new ArrayList<>();
        List<DashboardWidget> listWid =  dashboardWidgetRepository.findAll();
        if(!listWid.isEmpty()){
            for(DashboardWidget widget : listWid){
                ReferenceWidgetResponse response = new ReferenceWidgetResponse(widget.getWidgetId(),widget.getWidgetName());
                listRef.add(response);
            }
        }
        return listRef;
    }







}
