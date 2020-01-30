package com.scor.rr.service.Dashboard;

import com.scor.rr.domain.entities.Dashboard.DashboardWidgetColumns;
import com.scor.rr.domain.entities.Dashboard.UserDashboardWidgetColumns;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.Dashboard.UserDashboardWidgetColumnsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDashboardWidgetColumnsService {

    @Autowired
    private UserDashboardWidgetColumnsRepository userDashboardWidgetColumnsRepository;

    public void createWidgetColumns(List<DashboardWidgetColumns> columns, long userId, long widgetId){

        List<UserDashboardWidgetColumns> listCols = new ArrayList<>();

        if(columns != null && !columns.isEmpty()){
            for (DashboardWidgetColumns col: columns
            ) {
                UserDashboardWidgetColumns userDashboardWidgetColumns = new UserDashboardWidgetColumns();
                userDashboardWidgetColumns.setUserDashboardWidgetId(widgetId);
                userDashboardWidgetColumns.setUserID(userId);
                userDashboardWidgetColumns.setDashboardWidgetColumnName(col.getColumnName());
                userDashboardWidgetColumns.setDashboardWidgetColumnWidth(col.getDefaultWidth());
                userDashboardWidgetColumns.setDashboardWidgetColumnOrder(col.getColumnOrder());
                userDashboardWidgetColumns.setVisible(col.isVisible());
                userDashboardWidgetColumns.setSort(col.getSorting());
                userDashboardWidgetColumns.setSortType(col.getSortType());
                userDashboardWidgetColumns.setColumnHeader(col.getColumnHeader());
                listCols.add(userDashboardWidgetColumns);
            }
            userDashboardWidgetColumnsRepository.saveAll(listCols);
        }


    }

    public List<UserDashboardWidgetColumns> getWidgetColumns(long widgetId){
       return  userDashboardWidgetColumnsRepository.findByUserDashboardWidgetId(widgetId);

    }

    public List<UserDashboardWidgetColumns> duplicateColumns(List<UserDashboardWidgetColumns> columns) {
        List<UserDashboardWidgetColumns> listCols = new ArrayList<>();

        if(columns != null && !columns.isEmpty()){
            for (UserDashboardWidgetColumns col: columns
            ) {
                UserDashboardWidgetColumns userDashboardWidgetColumns = new UserDashboardWidgetColumns();
                userDashboardWidgetColumns.setUserDashboardWidgetId(col.getUserDashboardWidgetId());
                userDashboardWidgetColumns.setUserID(col.getUserID());
                userDashboardWidgetColumns.setDashboardWidgetColumnName(col.getDashboardWidgetColumnName());
                userDashboardWidgetColumns.setDashboardWidgetColumnWidth(col.getDashboardWidgetColumnWidth());
                userDashboardWidgetColumns.setDashboardWidgetColumnOrder(col.getDashboardWidgetColumnOrder());
                userDashboardWidgetColumns.setVisible(col.isVisible());
                userDashboardWidgetColumns.setSort(col.getSort());
                userDashboardWidgetColumns.setSortType(col.getSortType());
                userDashboardWidgetColumns.setColumnHeader(col.getColumnHeader());
                listCols.add(userDashboardWidgetColumns);
            }
            listCols = userDashboardWidgetColumnsRepository.saveAll(listCols);
        }
        return listCols;
    }

    public void deleteWidgetColumns(long id){
        userDashboardWidgetColumnsRepository.deleteById(id);
    }


}
