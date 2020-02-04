package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.entities.Dashboard.*;
import com.scor.rr.exceptions.Dashboard.DashboardWidgetNotFoundException;
import com.scor.rr.exceptions.Dashboard.UserDashboardNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.DashboardWidgetRepository;
import com.scor.rr.repository.Dashboard.UserDashboardRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetColumnsRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDashboardWidgetService {

    @Autowired
    private UserDashboardWidgetRepository userDashboardWidgetRepository;
    @Autowired
    private UserDashboardRepository userDashboardRepository;
    @Autowired
    private UserDashboardWidgetColumnsService userDashboardWidgetColumnsService;
    @Autowired
    private UserDashboardWidgetColumnsRepository userDashboardWidgetColumnsRepository;
    @Autowired
    private DashboardWidgetRepository dashboardWidgetRepository;
    @Autowired
    private DashboardWidgetColumnsService  dashboardWidgetColumnsService;

    public List<UserWidgetResponse> getDashboardWidget(long dashboardId) throws RRException{

        UserDashboard dashboard = userDashboardRepository.findByUserDashboardId(dashboardId);
        if(dashboard == null) throw new UserDashboardNotFoundException(dashboardId);

        List<UserWidgetResponse> listResponse = new ArrayList<>();

        List<UserDashboardWidget> dashboardWidgets = userDashboardWidgetRepository.findByUserDashboardId(dashboardId);
        if(!dashboardWidgets.isEmpty()){

            for(UserDashboardWidget widget : dashboardWidgets){
                UserWidgetResponse response = new UserWidgetResponse();
                response.setUserDashboardWidget(widget);
                response.setColumns(userDashboardWidgetColumnsService.getWidgetColumns(widget.getUserDashboardWidgetId()));
                listResponse.add(response);
            }
        }
        return listResponse;


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

        userDashboardWidgetColumnsService.createWidgetColumns(listCols,userId,widget.getUserDashboardWidgetId());

        List<UserDashboardWidgetColumns> listUserCols = userDashboardWidgetColumnsService.getWidgetColumns(widget.getUserDashboardWidgetId());


        return new UserWidgetResponse(widget,listUserCols);
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


        List<UserDashboardWidgetColumns> columns = userDashboardWidgetColumnsService.duplicateColumns(userDashboardWidgetColumnsRepository.findByUserDashboardWidgetId(widgetId),duplicate.getUserDashboardWidgetId());

        return new UserWidgetResponse(duplicate,columns);

    }

    public void UpdateDashboardWidget(UserDashboardWidget userDashboardWidget) {

        userDashboardWidgetRepository.save(userDashboardWidget);

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

    public void deleteByRef(long dashboardID, long referenceID) throws RRException {
        UserDashboard userDashboard = userDashboardRepository.findByUserDashboardId(dashboardID);
        if(userDashboard == null) throw new UserDashboardNotFoundException(dashboardID);

        List<UserDashboardWidget> widgets = userDashboardWidgetRepository.findByUserDashboardIdAndWidgetId(dashboardID,referenceID);
        if(!widgets.isEmpty()){
            for(UserDashboardWidget widget: widgets){
                deleteDashboardWidget(widget.getUserDashboardWidgetId());
            }
        }
    }
}
