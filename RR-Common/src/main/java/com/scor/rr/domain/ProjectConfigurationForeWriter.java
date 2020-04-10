package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProjectConfigurationForeWriter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriter {

    public ProjectConfigurationForeWriter(Integer entity,
                                          Long projectId,
                                          String carType,
                                          String carStatus,
                                          String carName,
                                          Date requestCreationDate,
                                          Long requestCreationBy,
                                          String requestCreationByFw,
                                          String code,
                                          String narrative) {
        this.entity = entity;
        this.projectId = projectId;
        this.carType = carType;
        this.carStatus = carStatus;
        this.carName = carName;
        this.requestCreationDate = requestCreationDate;
        this.requestedBy = requestCreationBy;
        this.requestedByFwId = requestCreationByFw;
        this.code = code;
        this.narrative = narrative;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterId")
    private Long projectConfigurationForeWriterId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "CARequestId", length = 30)
    private String caRequestId;

    @Column(name = "CARType", length = 15)
    private String carType;

    @Column(name = "CARStatus", length = 10)
    private String carStatus;

    @Column(name = "CARName")
    private String carName;

    @Column(name = "AssignedTo")
    private Long assignedTo;

    @CreatedDate
    @Column(name = "RequestCreationDate")
    private Date requestCreationDate;

    @Column(name = "RequestedBy")
    private Long requestedBy;

    @Column(name = "RequestedByFwId")
    private String requestedByFwId;

    @Column(name = "Code", length = 50)
    private String code;

    @Column(name = "Narrative", length = 500)
    private String narrative;


    //TODO: implement AuditorAware to persist createDate, CreatedBy, ...
}
