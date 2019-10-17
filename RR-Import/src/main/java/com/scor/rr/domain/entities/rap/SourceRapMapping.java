package com.scor.rr.domain.entities.rap;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the SourceRapMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceRapMapping")
@Data
public class SourceRapMapping {
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
