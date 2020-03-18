package com.scor.rr.service.Dashboard;

import com.google.gson.Gson;
import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.Response.DashboardChartResponse;
import com.scor.rr.domain.Response.DashboardDataResponse;
import com.scor.rr.domain.Response.UserWidgetResponse;
import com.scor.rr.domain.dto.DashboardChartData;
import com.scor.rr.domain.dto.DashboardRequest;
import com.scor.rr.domain.entities.Dashboard.*;
import com.scor.rr.domain.entities.DashboardView;
import com.scor.rr.exceptions.Dashboard.DashboardWidgetNotFoundException;
import com.scor.rr.exceptions.Dashboard.UserDashboardNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.DashboardWidgetRepository;
import com.scor.rr.repository.Dashboard.UserDashboardRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetColumnsRepository;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetRepository;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private DashboardWidgetColumnsService dashboardWidgetColumnsService;

    private StoredProcedureQuery fetchDataSource;

    @Autowired
    private EntityManager em;

    public List<UserWidgetResponse> getDashboardWidget(long dashboardId) throws RRException {

        UserDashboard dashboard = userDashboardRepository.findByUserDashboardId(dashboardId);
        if (dashboard == null) throw new UserDashboardNotFoundException(dashboardId);

        List<UserWidgetResponse> listResponse = new ArrayList<>();

        List<UserDashboardWidget> dashboardWidgets = userDashboardWidgetRepository.findByUserDashboardId(dashboardId);
        if (!dashboardWidgets.isEmpty()) {

            for (UserDashboardWidget widget : dashboardWidgets) {
                UserWidgetResponse response = new UserWidgetResponse();
                response.setUserDashboardWidget(widget);
                response.setColumns(userDashboardWidgetColumnsService.getWidgetColumns(widget.getUserDashboardWidgetId()));
                listResponse.add(response);
            }
        }
        return listResponse;


    }

    public UserWidgetResponse createDashboardWidget(long referenceId, long dashboardId) throws RRException {

        DashboardWidget dashboardWidget = dashboardWidgetRepository.findByWidgetId(referenceId);
        if (dashboardWidget == null) throw new DashboardWidgetNotFoundException(referenceId, "");

        List<DashboardWidgetColumns> listCols = dashboardWidgetColumnsService.getDashboardWidgetColumns(referenceId);

        UserDashboardWidget widget = new UserDashboardWidget();
        widget.setUserDashboardId(dashboardId);
        if(SecurityContextHolder.getContext() !=null && SecurityContextHolder.getContext().getAuthentication() !=null) {
            widget.setUserID(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId());
        }
        widget.setWidgetId(referenceId);
        widget.setUserAssignedName(dashboardWidget.getWidgetName());
        widget.setRowSpan(dashboardWidget.getRowSpan());
        widget.setColSpan(dashboardWidget.getColSpan());
        widget.setMinItemCols(dashboardWidget.getMinItemCols());
        widget.setMinItemRows(dashboardWidget.getMinItemRows());

        widget = userDashboardWidgetRepository.saveAndFlush(widget);

        userDashboardWidgetColumnsService.createWidgetColumns(listCols, widget.getUserDashboardWidgetId());

        List<UserDashboardWidgetColumns> listUserCols = userDashboardWidgetColumnsService.getWidgetColumns(widget.getUserDashboardWidgetId());


        return new UserWidgetResponse(widget, listUserCols);
    }

    public UserWidgetResponse duplicateWidget(long widgetId) throws RRException {

        UserDashboardWidget dashboardWidget = userDashboardWidgetRepository.findByUserDashboardWidgetId(widgetId);
        if (dashboardWidget == null) throw new DashboardWidgetNotFoundException(widgetId, "User");

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


        List<UserDashboardWidgetColumns> columns = userDashboardWidgetColumnsService.duplicateColumns(userDashboardWidgetColumnsRepository.findByUserDashboardWidgetId(widgetId), duplicate.getUserDashboardWidgetId());

        return new UserWidgetResponse(duplicate, columns);

    }

    public void UpdateDashboardWidget(UserDashboardWidget userDashboardWidget) {

        userDashboardWidgetRepository.save(userDashboardWidget);

    }

    public void deleteDashboardWidget(long id) throws RRException {
        UserDashboardWidget userDashboardWidget = userDashboardWidgetRepository.findByUserDashboardWidgetId(id);
        if (userDashboardWidget == null) throw new DashboardWidgetNotFoundException(id, "User");

        List<UserDashboardWidgetColumns> columns = userDashboardWidgetColumnsService.getWidgetColumns(id);
        if (columns != null && !columns.isEmpty()) {
            for (UserDashboardWidgetColumns col : columns) {
                userDashboardWidgetColumnsService.deleteWidgetColumns(col.getUserDashboardWidgetColumnId());
            }
        }
        userDashboardWidgetRepository.delete(userDashboardWidget);


    }

    public void deleteByRef(long dashboardID, long referenceID) throws RRException {
        UserDashboard userDashboard = userDashboardRepository.findByUserDashboardId(dashboardID);
        if (userDashboard == null) throw new UserDashboardNotFoundException(dashboardID);

        List<UserDashboardWidget> widgets = userDashboardWidgetRepository.findByUserDashboardIdAndWidgetId(dashboardID, referenceID);
        if (!widgets.isEmpty()) {
            for (UserDashboardWidget widget : widgets) {
                deleteDashboardWidget(widget.getUserDashboardWidgetId());
            }
        }
    }

    public DashboardDataResponse getDataForWidget(DashboardRequest request){

        if(request.getCarStatus().equals("Archived")){
            //might need to add one more
            request.setCarStatus("Completed','Superseded','Cancelled','Priced");
        }

        if(request.getCarStatus().equals("Chart")){
            request.setCarStatus("Completed','Superseded','Cancelled','Priced','In Progress','NEW");
        }

        DashboardDataResponse response = new DashboardDataResponse();
        response.setRefCount(getTotalCount(request));

        String  userCode = "";
        if(SecurityContextHolder.getContext() !=null && SecurityContextHolder.getContext().getAuthentication() !=null) {
            userCode=(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserCode());
        }
        response.setContent(userDashboardRepository.getDataForWidget(request.getCarStatus(),
                request.getEntity(),
                request.getUserDashboardWidgetId(),
                userCode,
                request.getPageNumber(),
                request.getPageSize(),
                request.isFilterByAnalyst()));
        return  response;
    }


    public int getTotalCount(DashboardRequest request) {

        String  userCode = "";
        if(SecurityContextHolder.getContext() !=null && SecurityContextHolder.getContext().getAuthentication() !=null) {
            userCode=(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserCode());
        }

        StoredProcedureQuery query = em.createStoredProcedureQuery("dbo.uspDashboardWidgetGetFiltredRecCount")
                .registerStoredProcedureParameter("CarStatus", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("Entity", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("UserDashboardWidgetId", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("UserCode", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("FilterByAnalyst", Boolean.class, ParameterMode.IN)
                .registerStoredProcedureParameter("FilteredRecCount", Integer.class, ParameterMode.OUT);

                query.setParameter("CarStatus", request.getCarStatus())
                .setParameter("Entity", request.getEntity())
                .setParameter("UserDashboardWidgetId", request.getUserDashboardWidgetId())
                .setParameter("FilterByAnalyst", request.isFilterByAnalyst())
                .setParameter("UserCode", userCode).execute();

        try {
            query.execute();
            return (Integer) query.getOutputParameterValue("FilteredRecCount");

        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }


    }}

//    public List<DashboardChartResponse>  getChartData(Date from, Date to){
//        List<Map<String,Object>>data = userDashboardWidgetRepository.getDataForChart(from,to);
//        List<DashboardChartResponse> response = new ArrayList<>();
//        String assignedName = " ";
//        int counter = 0 ;
//        if(data != null){
//            DashboardChartResponse res = new DashboardChartResponse();
//            for(Map<String,Object> line : data){
//            for (Map.Entry<String, Object> entry : line.entrySet()) {
//
//                if(!assignedName.equals(line.getAssignedAnalyst()) && counter != 0){
//                    response.add(res);
//                    res = new DashboardChartResponse();
//                }
//                    assignedName = line.getAssignedAnalyst();
//                    res.setAssignedAnalyst(line.getAssignedAnalyst());
//                    switch (line.getCarStatus()){
//                        case "NEW" : res.setNewCars(line.getNumberCarsPerAnalyst());
//                            break;
//                        case "Cancelled" : res.setCancelled(line.getNumberCarsPerAnalyst());
//                            break;
//                        case "Priced" : res.setPriced(line.getNumberCarsPerAnalyst());
//                            break;
//                        case "Superseded" : res.setSuperseded(line.getNumberCarsPerAnalyst());
//                            break;
//                        case "InProgress" : res.setInProgress(line.getNumberCarsPerAnalyst());
//                            break;
//                        case "Completed" : res.setCompleted(line.getNumberCarsPerAnalyst());
//                            break;
//                        default: break;
//                    }
//                    counter ++;
//
//                    if(counter == data.size()){
//                        response.add(res);
//                    }
//
//
//
//
//            }
//        }
//        return response;
//    }
//}
