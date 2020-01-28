package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="DashboardWidgetParameter")
public class DashboardWidgetParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DashboardWidgetParameterId", nullable = false)
    private long dashboardWidgetParameterId;

    @Column(name = "DashboardWidgetId")
    private long dashboardWidgetId;

    @Column(name = "ParameterName")
    private String parameterName;

    @Column(name = "ParameterType")
    private String parameterType;

    @Column(name = "ParameterValue")
    private String parameterValue;

    public DashboardWidgetParameter() {
    }

    public DashboardWidgetParameter(long dashboardWidgetId, String parameterName, String parameterType, String parameterValue) {
        this.dashboardWidgetId = dashboardWidgetId;
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
    }
}
