package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name="UserDashboard")
public class UserDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDashboardId", nullable = false)
    private long userDashboardId;

    @Column(name = "UserId")
    private long userId;

    @Column(name = "DashboardName")
    private String dashboardName;

    @Column(name = "SearchMode")
    private String searchMode;

    @Column(name = "IsVisible")
    private boolean isVisible;

    @Column(name = "DashBoardSequence")
    private int dashBoardSequence;

    public UserDashboard() {
    }

}
