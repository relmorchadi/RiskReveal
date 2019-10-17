package com.scor.rr.domain.entities.references.cat.mapping.region;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the TTRegionPerilMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "TTRegionPerilMapping")
@Data
public class RegionPerilMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RegionPerilMappingId")
    private Long regionPerilMappingId;
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
    @Column(name = "RegionPerilId")
    private Integer regionPerilId;
}
