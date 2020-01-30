package com.scor.rr.domain.entities.Dashboard;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="ZZ_UserDashboardWidgetParameter")
public class UserDashboardWidgetParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserDashboardWidgetParameterId", nullable = false)
    private long userDashboardWidgetParameterId;

    @Column(name = "UserDashboardWidgetId")
    private long userDashboardWidgetId;

    @Column(name = "ParameterName")
    private String parameterName;

    @Column(name = "UserID")
    private long userID;

    @Column(name = "ParameterType")
    private String parameterType;

    @Column(name = "ParameterValue")
    private String parameterValue;

    public UserDashboardWidgetParameter() {
    }

}
