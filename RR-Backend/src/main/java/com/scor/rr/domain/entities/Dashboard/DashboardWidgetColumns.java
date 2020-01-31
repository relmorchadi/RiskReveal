package com.scor.rr.domain.entities.Dashboard;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ZZ_DashboardWidgetColumns")
public class DashboardWidgetColumns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DashboardWidgetColumnId", nullable = false)
    private long dashboardWidgetColumnId;

    @Column(name = "DashboardWidgetId")
    private long dashboardWidgetId;

    @Column(name = "ColumnName")
    private String columnName;

    @Column(name = "ColumnOrder")
    private int columnOrder;

    @Column(name ="ColumnHeader")
    private String columnHeader;

    @Column(name = "IsVisible")
    private boolean isVisible;

    @Column(name = "DataType")
    private String dataType;

    @Column(name = "MinWidth")
    private int minWidth;

    @Column(name = "MaxWidth")
    private int maxWidth;

    @Column(name = "DefaultWidth")
    private int defaultWidth;

    @Column(name = "Sorting", nullable = true)
    private int sorting;

    @Column(name = "SortType", nullable = true)
    private String sortType;

    public DashboardWidgetColumns() {
    }

    public DashboardWidgetColumns(long dashboardWidgetId, String columnName, int columnOrder, boolean isVisible, String dataType, int minWidth, int maxWidth, int defaultWidth, int sorting, String sortType) {
        this.dashboardWidgetId = dashboardWidgetId;
        this.columnName = columnName;
        this.columnOrder = columnOrder;
        this.isVisible = isVisible;
        this.dataType = dataType;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.defaultWidth = defaultWidth;
        this.sorting = sorting;
        this.sortType = sortType;
    }
}
