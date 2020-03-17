package com.scor.rr.service.Dashboard;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.entities.Dashboard.DashboardWidgetColumns;
import com.scor.rr.domain.entities.Dashboard.UserDashboardWidgetColumns;
import com.scor.rr.domain.requests.OrderAndVisibilityRequest;
import com.scor.rr.exceptions.Dashboard.UserDashboardColumnNotFoundException;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetColumnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDashboardWidgetColumnsService {

    @Autowired
    private UserDashboardWidgetColumnsRepository userDashboardWidgetColumnsRepository;

    public void createWidgetColumns(List<DashboardWidgetColumns> columns, long widgetId) {

        List<UserDashboardWidgetColumns> listCols = new ArrayList<>();

        if (columns != null && !columns.isEmpty()) {
            for (DashboardWidgetColumns col : columns
            ) {
                UserDashboardWidgetColumns userDashboardWidgetColumns = new UserDashboardWidgetColumns();
                userDashboardWidgetColumns.setUserDashboardWidgetId(widgetId);
                if(SecurityContextHolder.getContext() !=null && SecurityContextHolder.getContext().getAuthentication() !=null) {
                    userDashboardWidgetColumns.setUserID(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId());
                }
                userDashboardWidgetColumns.setDataType(col.getDataType());
                userDashboardWidgetColumns.setDashboardWidgetColumnName(col.getColumnName());
                userDashboardWidgetColumns.setDashboardWidgetColumnWidth(col.getDefaultWidth());
                userDashboardWidgetColumns.setDashboardWidgetColumnOrder(col.getColumnOrder());
                userDashboardWidgetColumns.setVisible(col.isVisible());
                userDashboardWidgetColumns.setSort(col.getSorting());
                userDashboardWidgetColumns.setSortType(col.getSortType());
                userDashboardWidgetColumns.setDataColumnType(col.getDataColumnType());
                userDashboardWidgetColumns.setColumnHeader(col.getColumnHeader());
                listCols.add(userDashboardWidgetColumns);
            }
            userDashboardWidgetColumnsRepository.saveAll(listCols);
        }


    }

    public List<UserDashboardWidgetColumns> getWidgetColumns(long widgetId) {
        return userDashboardWidgetColumnsRepository.findByUserDashboardWidgetId(widgetId);

    }

    public List<UserDashboardWidgetColumns> duplicateColumns(List<UserDashboardWidgetColumns> columns, long newWidgetId) {
        List<UserDashboardWidgetColumns> listCols = new ArrayList<>();

        if (columns != null && !columns.isEmpty()) {
            for (UserDashboardWidgetColumns col : columns
            ) {
                UserDashboardWidgetColumns userDashboardWidgetColumns = new UserDashboardWidgetColumns();
                userDashboardWidgetColumns.setUserDashboardWidgetId(newWidgetId);
                userDashboardWidgetColumns.setUserID(col.getUserID());
                userDashboardWidgetColumns.setDataType(col.getDataType());
                userDashboardWidgetColumns.setDashboardWidgetColumnName(col.getDashboardWidgetColumnName());
                userDashboardWidgetColumns.setDashboardWidgetColumnWidth(col.getDashboardWidgetColumnWidth());
                userDashboardWidgetColumns.setDashboardWidgetColumnOrder(col.getDashboardWidgetColumnOrder());
                userDashboardWidgetColumns.setVisible(col.isVisible());
                userDashboardWidgetColumns.setSort(col.getSort());
                userDashboardWidgetColumns.setSortType(col.getSortType());
                userDashboardWidgetColumns.setDataColumnType(col.getDataColumnType());
                userDashboardWidgetColumns.setColumnHeader(col.getColumnHeader());
                listCols.add(userDashboardWidgetColumns);
            }
            listCols = userDashboardWidgetColumnsRepository.saveAll(listCols);
        }
        return listCols;
    }

    public void deleteWidgetColumns(long id) {
        userDashboardWidgetColumnsRepository.deleteById(id);
    }

    public void updateColumnWidth(long columnId, int columnWidth) throws RRException {
//        UserDashboardWidgetColumns userDashboardWidgetColumns = userDashboardWidgetColumnsRepository.findByUserDashboardWidgetColumnId(columnId);
//        if (userDashboardWidgetColumns == null) throw new UserDashboardColumnNotFoundException(columnId);
//
//        userDashboardWidgetColumns.setDashboardWidgetColumnWidth(columnWidth);
//        userDashboardWidgetColumnsRepository.save(userDashboardWidgetColumns);
        userDashboardWidgetColumnsRepository.updateDashboardWidgetColumnWidth(columnId,columnWidth);
    }

    public void resetFilterForWidget(long widgetId){
        userDashboardWidgetColumnsRepository.resetFilter(widgetId);
    }

    public void resetSortForWidget(long widgetId){
        userDashboardWidgetColumnsRepository.resetSort(widgetId);
    }

    public void updateColumnFilter(long columnId, String filter) throws RRException {
//        UserDashboardWidgetColumns userDashboardWidgetColumns = userDashboardWidgetColumnsRepository.findByUserDashboardWidgetColumnId(columnId);
//        if (userDashboardWidgetColumns == null) throw new UserDashboardColumnNotFoundException(columnId);
//
//        userDashboardWidgetColumns.setFilterCriteria(filter);
//        userDashboardWidgetColumnsRepository.save(userDashboardWidgetColumns);
        userDashboardWidgetColumnsRepository.updateDashboardWidgetColumnFilter(columnId,filter);


    }

    public void updateColumnSort(long columnId, int sort,String sortType) throws RRException {
//        UserDashboardWidgetColumns userDashboardWidgetColumns = userDashboardWidgetColumnsRepository.findByUserDashboardWidgetColumnId(columnId);
//        if (userDashboardWidgetColumns == null) throw new UserDashboardColumnNotFoundException(columnId);
//
//        userDashboardWidgetColumns.setSort(sort);
//        userDashboardWidgetColumns.setSortType(sortType);
//        userDashboardWidgetColumnsRepository.save(userDashboardWidgetColumns);
        userDashboardWidgetColumnsRepository.updateDashboardWidgetColumnSort(columnId,sort,sortType);


    }

    public void updateOrderAndVisibility(List<OrderAndVisibilityRequest> request) throws RRException {

//        if(!request.isEmpty()){
//            List<UserDashboardWidgetColumns> list = new ArrayList<>();
//            for(OrderAndVisibilityRequest row : request){
//                UserDashboardWidgetColumns userDashboardWidgetColumns = userDashboardWidgetColumnsRepository.findByUserDashboardWidgetColumnId(row.getColumnId());
//                if (userDashboardWidgetColumns == null) throw new UserDashboardColumnNotFoundException(row.getColumnId());
//
//                userDashboardWidgetColumns.setDashboardWidgetColumnOrder(row.getOrder());
//                userDashboardWidgetColumns.setVisible(row.isVisible());
//                list.add(userDashboardWidgetColumns);
//
//            }
//            userDashboardWidgetColumnsRepository.saveAll(list);
//        }
        int counter = 0;

        System.out.println("this is the request size" + request.size());

        if(!request.isEmpty()){
            String columnIdArray = "";
            String isVisibleArray = "";
            String orderArray = "";

            counter = request.size() + 1;

            for(OrderAndVisibilityRequest row : request){
                UserDashboardWidgetColumns userDashboardWidgetColumns = userDashboardWidgetColumnsRepository.findByUserDashboardWidgetColumnId(row.getColumnId());
                if (userDashboardWidgetColumns == null) throw new UserDashboardColumnNotFoundException(row.getColumnId());

                columnIdArray = columnIdArray + "," + userDashboardWidgetColumns.getUserDashboardWidgetColumnId();
                isVisibleArray = isVisibleArray + "," + row.isVisible();
                orderArray = orderArray + "," + row.getOrder();



            }
            userDashboardWidgetColumnsRepository.updateDashboardWidgetColumnVisibilityAndOrder(columnIdArray,isVisibleArray,orderArray,counter);

    }}






}
