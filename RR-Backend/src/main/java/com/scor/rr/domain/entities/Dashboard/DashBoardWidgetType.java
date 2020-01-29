package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ZZ_DashBoardWidgetType")
public class DashBoardWidgetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DashboardWidgetTypeId", nullable = false)
    private long dashboardWidgetTypeId;

    @Column(name = "DashboardWidgetType", nullable = false)
    private String dashboardWidgetType;

    public DashBoardWidgetType(String dashboardWidgetType) {
        this.dashboardWidgetType = dashboardWidgetType;
    }

    public DashBoardWidgetType() {
    }
}
