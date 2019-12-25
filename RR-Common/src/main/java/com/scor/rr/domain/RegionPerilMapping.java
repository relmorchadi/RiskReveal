package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZZ_RegionPerilMapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionPerilMapping {

    @Id
    @Column(name = "RegionPerilId")
    private Long regionPerilID;
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

}
