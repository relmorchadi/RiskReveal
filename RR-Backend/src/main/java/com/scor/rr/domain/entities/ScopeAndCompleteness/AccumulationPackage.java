package com.scor.rr.domain.entities.ScopeAndCompleteness;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="AccumulationPackage")
public class AccumulationPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccumulationPackageId", nullable = false)
    private long accumulationPackageId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "WorkspaceId")
    private long workspaceId;

    @Column(name = "CreatedOn")
    private Date createdOn;

    @Column(name = "CreatedBy")
    private long createdBy;

    @Column(name = "LastModifiedOn")
    private Date lastModifiedOn;

    @Column(name = "LastModifiedBy")
    private long lastModifiedBy;

    @Column(name = "PublishedOn")
    private Date publishedOn;

    @Column(name = "PublishedBy")
    private long publishedBy;

    @Column(name = "AccumulationPackageVersion")
    private int accumulationPackageVersion;

    @Column(name = "AccumulationPackageStatus")
    private String accumulationPackageStatus;

    @Column(name = "AccumulationPackageNarrative")
    private String accumulationPackageNarrative;

    public AccumulationPackage() {
    }
}
