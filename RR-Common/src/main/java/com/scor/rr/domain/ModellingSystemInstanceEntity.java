package com.scor.rr.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModellingSystemInstance database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModellingSystemInstance")
@Data
public class ModellingSystemInstanceEntity {
    @Id
    @Column(name = "ModellingSystemInstanceId")
    private String modellingSystemInstanceId;
    @Column(name = "Name")
    private String name;
    @Column(name = "LocationAddress")
    private String locationAddress;
    @Column(name = "DefaultLocation")
    private Boolean defaultLocation;
    @Column(name = "Login")
    private String login;
    @Column(name = "Pass")
    private String pass;
    @Column(name = "URL")
    private String url;
    @Column(name = "TestQuery")
    private String testQuery;
    @Column(name = "DriverClass")
    private String driverClass;
    @Column(name = "Acitve")
    private Boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ModellingSystemVersionId")
    private ModellingSystemVersionEntity modellingSystemVersion;
}
