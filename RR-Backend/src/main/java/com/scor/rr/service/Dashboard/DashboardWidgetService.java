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
    @Autowired
    private DashboardWidgetTypeService dashboardWidgetTypeService;
    @Autowired
    private DashboardWidgetColumnsService dashboardWidgetColumnsService;
    @Autowired
    private UserDashboardWidgetColumnsService userDashboardWidgetColumnsService;
    @Autowired
    private UserDashboardWidgetRepository userDashboardWidgetRepository;
    @Autowired
    private UserDashboardWidgetColumnsRepository userDashboardWidgetColumnsRepository;

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

    public UserWidgetResponse createDashboardWidget(long referenceId, long dashboardId, long userId) throws RRException {

        DashboardWidget dashboardWidget = dashboardWidgetRepository.findByWidgetId(referenceId);
        if(dashboardWidget == null) throw new DashboardWidgetNotFoundException(referenceId,"");

        List<DashboardWidgetColumns> listCols = dashboardWidgetColumnsService.getDashboardWidgetColumns(referenceId);

        UserDashboardWidget widget = new UserDashboardWidget();
        widget.setUserDashboardId(dashboardId);
        widget.setUserID(userId);
        widget.setWidgetId(referenceId);
        widget.setUserAssignedName(dashboardWidget.getWidgetName());
        widget.setRowSpan(dashboardWidget.getRowSpan());
        widget.setColSpan(dashboardWidget.getColSpan());
        widget.setMinItemCols(dashboardWidget.getMinItemCols());
        widget.setMinItemRows(dashboardWidget.getMinItemRows());

        widget = userDashboardWidgetRepository.saveAndFlush(widget);

        userDashboardWidgetColumnsService.createWidgetColumns(listCols,userId,widget.getWidgetId());

        List<UserDashboardWidgetColumns> listUserCols = userDashboardWidgetColumnsService.getWidgetColumns(widget.getWidgetId());


        return new UserWidgetResponse(widget,listUserCols);
    }

    public void UpdateDashboardWidget(UserWidgetResponse userWidgetResponse) throws RRException {

        userDashboardWidgetRepository.save(userWidgetResponse.getUserDashboardWidget());
        userDashboardWidgetColumnsRepository.saveAll(userWidgetResponse.getColumns());
    }

    public void deleteDashboardWidget(long id) throws RRException{
        UserDashboardWidget userDashboardWidget = userDashboardWidgetRepository.findByUserDashboardWidgetId(id);
        if(userDashboardWidget == null) throw new DashboardWidgetNotFoundException(id,"User");

        List<UserDashboardWidgetColumns> columns = userDashboardWidgetColumnsService.getWidgetColumns(id);
        if(columns != null && !columns.isEmpty()){
            for(UserDashboardWidgetColumns col : columns){
                userDashboardWidgetColumnsService.deleteWidgetColumns(col.getUserDashboardWidgetColumnId());
            }
        }
        userDashboardWidgetRepository.delete(userDashboardWidget);


    }

    public UserWidgetResponse duplicateWidget(long widgetId) throws RRException {

        UserDashboardWidget dashboardWidget = userDashboardWidgetRepository.findByUserDashboardWidgetId(widgetId);
        if(dashboardWidget == null) throw new DashboardWidgetNotFoundException(widgetId,"User");

        UserDashboardWidget duplicate = new UserDashboardWidget();
        duplicate.setUserDashboardId(dashboardWidget.getUserDashboardId());
        duplicate.setUserID(dashboardWidget.getUserID());
        duplicate.setWidgetId(dashboardWidget.getWidgetId());
        duplicate.setUserAssignedName(dashboardWidget.getUserAssignedName());
        duplicate.setRowSpan(dashboardWidget.getRowSpan());
        duplicate.setColSpan(dashboardWidget.getColSpan());
        duplicate.setMinItemRows(dashboardWidget.getMinItemRows());
        duplicate.setMinItemCols(dashboardWidget.getMinItemCols());

        duplicate = userDashboardWidgetRepository.saveAndFlush(duplicate);


        List<UserDashboardWidgetColumns> columns = userDashboardWidgetColumnsService.duplicateColumns(userDashboardWidgetColumnsRepository.findByUserDashboardWidgetId(widgetId));

        return new UserWidgetResponse(duplicate,columns);

    }
}
