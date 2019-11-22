package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionPerilMapping {

    @Id
    private Integer regionPerilID;

    private String modellingVendor;

    private String modellingSystem;

    private String modellingSystemVersion;

    private String countryCode;

    private String countryDesc;

    private String admin1Code;

    private String admin1Desc;

    private String perilCode;

}
