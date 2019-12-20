package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ZZ_SourceRAPMapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceRapMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SourceRapMappingId")
    private Long sourceRapMappingId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;
    @Column(name = "SourceRapCode")
    private String sourceRapCode;
    @Column(name = "ProfileKey")
    private String profileKey;
}
