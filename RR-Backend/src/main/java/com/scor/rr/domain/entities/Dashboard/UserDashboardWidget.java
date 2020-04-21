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

    @Column(name = "DashboardWidgetId")
    private long widgetId;

    @Column(name = "UserAssignedName")
    private String userAssignedName;

    @Column(name = "RowPosition")
    private int rowPosition;

    @Column(name = "ColPosition")
    private int colPosition;

    @Column(name = "UserID")
    private long userID;

    @Column(name = "RowSpan")
    private int rowSpan;

    @Column(name = "ColSpan")
    private int colSpan;

    @Column(name = "MinItemRows")
    private int minItemRows;

    @Column(name = "MinItemCols")
    private int minItemCols;

    public UserDashboardWidget() {
    }


}
