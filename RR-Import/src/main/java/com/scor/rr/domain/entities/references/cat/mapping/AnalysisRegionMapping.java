package com.scor.rr.domain.entities.references.cat.mapping;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the AnalysisRegionMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AnalysisRegionMapping")
@Data
public class AnalysisRegionMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnalysisRegionMappingId")
    private Long analysisRegionMappingId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "CountryDesc")
    private String countryDesc;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "Admin1Desc")
    private String admin1Desc;
    @Column(name = "PerilCode")
    private String perilCode;
    @Column(name = "AnalysisRegionCode")
    private String analysisRegionCode;
}
