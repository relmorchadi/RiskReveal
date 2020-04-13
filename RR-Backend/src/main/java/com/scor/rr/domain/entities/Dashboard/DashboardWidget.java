package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="DashBoardWidget")
public class DashboardWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WidgetId", nullable = false)
    private long widgetId;

    @Column(name = "WidgetName")
    private String widgetName;

    @Column(name = "WidgetType")
    private String widgetType;

    @Column(name = "WidgetDefaultDisplayName")
    private String widgetDefaultDisplayName;

    @Column(name = "DataSourceType")
    private String dataSourceType;

    @Column(name = "DataSource")
    private String dataSource;

    @Column(name = "RowSpan")
    private int rowSpan;

    @Column(name ="WidgetMode")
    private String widgetMode;

    @Column(name = "ColSpan")
    private int colSpan;

    @Column(name = "MinItemRows")
    private int minItemRows;

    @Column(name = "MinItemCols")
    private int minItemCols;

    public DashboardWidget() {
    }

}
