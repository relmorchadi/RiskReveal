package com.scor.rr.domain.TargetBuild.AccumulationPackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AccumulationPackage", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccumulationPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccumulationPackageId")
    private Long accumulationPackageId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @CreatedDate
    @Column(name = "CreatedOn")
    private Date createdOn;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @Column(name = "LastModifiedOn")
    private Date lastModifiedOn;

    @Column(name = "LastModifiedBy")
    private Integer lastModifiedBy;

    @Column(name = "PublishedOn")
    private Date publishedOn;

    @Column(name = "PublishedBy")
    private Integer publishedBy;

    @Column(name = "AccumulationPackageVersion")
    private Integer accumulationPackageVersion;

    @Column(name = "AccumulationPackageStatus", length = 15)
    private String accumulationPackageStatus;

    @Column(name = "AccumulationPackageNarrative")
    private String accumulationPackageNarrative;

}
