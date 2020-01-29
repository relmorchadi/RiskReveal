package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ZZ_DashBoardWidget")
public class DashboardWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WidgetId", nullable = false)
    private long widgetId;

    @Column(name = "WidgetName")
    private String widgetName;

    @Column(name = "WidgetType")
    private long widgetType;

    @Column(name = "WidgetDefaultDisplayName")
    private String widgetDefaultDisplayName;

    @Column(name = "DataSourceType")
    private String dataSourceType;

    @Column(name = "DataSource")
    private String dataSource;

    public DashboardWidget() {
    }

    public DashboardWidget(String widgetName, long widgetType, String widgetDefaultDisplayName, String dataSourceType, String dataSource) {
        this.widgetName = widgetName;
        this.widgetType = widgetType;
        this.widgetDefaultDisplayName = widgetDefaultDisplayName;
        this.dataSourceType = dataSourceType;
        this.dataSource = dataSource;
    }
}
