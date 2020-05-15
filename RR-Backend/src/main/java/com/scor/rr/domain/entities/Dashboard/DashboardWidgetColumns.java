package com.scor.rr.domain.entities.Dashboard;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="DashboardWidgetColumns")
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

    @Column(name = "Sorting")
    private int sorting;

    @Column(name = "DataColumnType")
    private String dataColumnType;

    @Column(name = "SortType")
    private String sortType;

    public DashboardWidgetColumns() {
    }

}
