package com.scor.rr.repository.Dashboard;

import com.scor.rr.domain.entities.Dashboard.UserDashboardWidgetColumns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface UserDashboardWidgetColumnsRepository extends JpaRepository<UserDashboardWidgetColumns,Long> {

    List<UserDashboardWidgetColumns> findByUserDashboardWidgetId(long id);
    UserDashboardWidgetColumns findByUserDashboardWidgetColumnId(long id);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.update_Widget_table_width @columnId=:columnId,  @width=:width", nativeQuery = true)
    void updateDashboardWidgetColumnWidth(@Param("columnId") long columnId, @Param("width") int width);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.usp_Reset_Sort @widgetId=:widgetId,", nativeQuery = true)
    void resetSort(@Param("widgetId") long widgetId);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.usp_Reset_Filter @widgetId=:widgetId,", nativeQuery = true)
    void resetFilter(@Param("widgetId") long widgetId);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.update_Widget_table_sort @columnId=:columnId,  @sort=:sort, @sorting=:sorting", nativeQuery = true)
    void updateDashboardWidgetColumnSort(@Param("columnId") long columnId, @Param("sort") int sort, @Param("sorting") String sorting);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.update_Widget_table_filter @columnId=:columnId, @filter=:filter", nativeQuery = true)
    void updateDashboardWidgetColumnFilter(@Param("columnId") long columnId, @Param("filter") String filter);

    @Transactional
    @Modifying
    @Query(value = "exec dbonew.update_Widget_table_visibleOrder @columnIdArray=:columnIdArray, @visibleArray=:visibleArray, @orderArray=:orderArray, @counter=:counter", nativeQuery = true)
    void updateDashboardWidgetColumnVisibilityAndOrder(@Param("columnIdArray") String columnIdArray, @Param("visibleArray") String isVisibleArray,@Param("orderArray") String orderArray,@Param("counter") int counter);

}
