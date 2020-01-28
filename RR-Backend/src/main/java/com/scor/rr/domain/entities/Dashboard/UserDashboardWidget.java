package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="UserDashboardWidget")
public class UserDashboardWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDashboardWidgetId", nullable = false)
    private long userDashboardWidgetId;

    @Column(name = "UserDashboardId")
    private long userDashboardId;

    @Column(name = "WidgetId")
    private long widgetId;

    @Column(name = "UserAssignedName")
    private String userAssignedName;

    @Column(name = "RowPosition")
    private int rowPosition;

    @Column(name = "ColPosition")
    private int colPosition;

    @Column(name = "RowSpan")
    private int rowSpan;

    @Column(name = "ColSpan")
    private int colSpan;

    @Column(name = "MinItemRows")
    private int minItemRows;

    @Column(name = "MinItemCols")
    private int minItemCols;

    @Column(name = "ComponentName")
    private String componentName;

    public UserDashboardWidget() {
    }

    public UserDashboardWidget(long userDashboardId, long widgetId, String userAssignedName, int rowPosition, int colPosition, int rowSpan, int colSpan, int minItemRows, int minItemCols, String componentName) {
        this.userDashboardId = userDashboardId;
        this.widgetId = widgetId;
        this.userAssignedName = userAssignedName;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.minItemRows = minItemRows;
        this.minItemCols = minItemCols;
        this.componentName = componentName;
    }
}
