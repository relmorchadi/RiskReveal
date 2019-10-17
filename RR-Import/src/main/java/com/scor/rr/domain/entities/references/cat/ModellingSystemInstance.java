package com.scor.rr.domain.entities.references.cat;

import com.scor.rr.domain.entities.plt.ModellingSystemVersion;
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
public class ModellingSystemInstance {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemVersionId")
    private ModellingSystemVersion modellingSystemVersion;
}
