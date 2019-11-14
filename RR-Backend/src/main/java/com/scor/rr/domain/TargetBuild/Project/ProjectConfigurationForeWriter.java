package com.scor.rr.domain.TargetBuild.Project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProjectConfigurationForeWriter", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterId")
    private Integer projectConfigurationForeWriterId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectId")
    private Integer projectId;

    @Column(name = "CARequestId", length = 30)
    private String caRequestId;

    @Column(name = "CARType", length = 15)
    private String carType;

    @Column(name = "CARStatus", length = 10)
    private String carStatus;

    @Column(name = "CARName")
    private String carName;

    @Column(name = "AssignedTo")
    private Integer assignedTo;

    @Column(name = "LastUpdateBy", nullable = false)
    private Integer lastUpdateBy;

    @LastModifiedDate
    @Column(name = "LastUpdateDate", nullable = false)
    private Date lastUpdateDate;

    @CreatedDate
    @Column(name = "RequestCreationDate")
    private Date requestCreationDate;

    @Column(name = "RequestCreationBy")
    private Integer requestCreationBy;

    @Column(name = "Code", length = 50)
    private String code;

    @Column(name = "Narrative", length = 500)
    private String narrative;


    //TODO: implement AuditorAware to persist createDate, CreatedBy, ...
}
