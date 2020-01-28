package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="UserDashboardWidgetParameter")
public class UserDashboardWidgetParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDashboardWidgetParameterId", nullable = false)
    private long userDashboardWidgetParameterId;

    @Column(name = "UserDashboardWidgetId")
    private long userDashboardWidgetId;

    @Column(name = "ParameterName")
    private String parameterName;

    @Column(name = "ParameterType")
    private String parameterType;

    @Column(name = "ParameterValue")
    private String parameterValue;

    public UserDashboardWidgetParameter() {
    }

    public UserDashboardWidgetParameter(long userDashboardWidgetId, String parameterName, String parameterType, String parameterValue) {
        this.userDashboardWidgetId = userDashboardWidgetId;
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
    }
}
