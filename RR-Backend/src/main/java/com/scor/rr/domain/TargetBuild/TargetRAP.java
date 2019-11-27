package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TargetRAP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetRAP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TargetRAPId")
    private Long targetRAPId;

    @Column(name = "ModellingVendor")
    private String modellingVendor;

    @Column(name = "ModellingSystem")
    private String modellingSystem;

    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;

    @Column(name = "TargetRAPCode")
    private String targetRAPCode;

    @Column(name = "TargetRAPDesc")
    private String targetRAPDesc;

    @Column(name = "PETId")
    private Integer petId;

    @Column(name = "SourceRAPCode")
    private String sourceRAPCode;

    @Column(name = "IsSCORGenerated")
    private Boolean isSCORGenerated;

    @Column(name = "IsSCORCurrent")
    private Boolean isSCORCurrent;

    @Column(name = "IsSCORDefault")
    private Boolean isSCORDefault;

    @Column(name = "IsActive")
    private Boolean isActive;

}
