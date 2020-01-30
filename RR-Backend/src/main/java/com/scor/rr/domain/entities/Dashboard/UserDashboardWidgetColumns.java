package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ZZ_UserDashboardWidgetColumns")
public class UserDashboardWidgetColumns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDashboardWidgetColumnId", nullable = false)
    private long userDashboardWidgetColumnId;

    @Column(name = "UserDashboardWidgetId")
    private long userDashboardWidgetId;

    @Column(name = "DashboardWidgetColumnName")
    private String dashboardWidgetColumnName;

    @Column(name = "DashboardWidgetColumnWidth")
    private int dashboardWidgetColumnWidth;

    @Column(name = "DashboardWidgetColumnOrder")
    private int dashboardWidgetColumnOrder;

    @Column(name = "IsVisible")
    private boolean isVisible;

    @Column(name = "UserID")
    private long userID;

    @Column(name = "ColumnHeader")
    private String columnHeader;

    @Column(name = "FilterCriteria")
    private String filterCriteria;

    @Column(name = "Sort")
    private int sort;

    @Column(name = "SortType")
    private String sortType;

    public UserDashboardWidgetColumns() {
    }
}
