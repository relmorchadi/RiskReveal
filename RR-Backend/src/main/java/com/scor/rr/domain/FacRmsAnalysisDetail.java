//package com.scor.rr.domain;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import java.math.BigDecimal;
//
//@Data
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class FacRmsAnalysisDetail {
//    @Id
//    @Column(name = "id")
//    private Integer id;
//    @Column(name = "rdm_id")
//    private Integer rdmId;
//    @Column(name = "rdm_name")
//    private String rdmName;
//    @Column(name = "analysis_id")
//    private Integer analysisId;
//    @Column(name = "analysis_name")
//    private String analysisName;
//    @Column(name = "analysis_description")
//    private String analysisDescription;
//    @Column(name = "default_grain")
//    private String defaultGrain;
//    @Column(name = "exposure_type")
//    private String exposureType;
//    @Column(name = "exposuretype_code")
//    private short exposuretypeCode;
//    @Column(name = "source_edm_name")
//    private String sourceEdmName;
//    @Column(name = "exposure_id")
//    private int exposureId;
//    @Column(name = "analysis_ccy")
//    private String analysisCcy;
//    @Column(name = "ccy_rate")
//    private Double ccyRate;
//    @Column(name = "type_code")
//    private short typeCode;
//    @Column(name = "analysis_type")
//    private String analysisType;
//    @Column(name = "run_date")
//    private String runDate;
//    @Column(name = "region")
//    private String region;
//    @Column(name = "peril")
//    private String peril;
//    @Column(name = "source_region_peril")
//    private String sourceRegionPeril;
//    @Column(name = "sub_peril")
//    private String subPeril;
//    @Column(name = "loss_amplification")
//    private String lossAmplification;
//    @Column(name = "status")
//    private short status;
//    @Column(name = "analysis_mode")
//    private short analysisMode;
//    @Column(name = "enginetype_code")
//    private short enginetypeCode;
//    @Column(name = "engine_type")
//    private String engineType;
//    @Column(name = "engine_version")
//    private String engineVersion;
//    @Column(name = "engine_version_major")
//    private String engineVersionMajor;
//    @Column(name = "profile_name")
//    private String profileName;
//    @Column(name = "profile_key")
//    private String profileKey;
//    @Column(name = "has_multi_region_perils")
//    private int hasMultiRegionPerils;
//    @Column(name = "is_valid_for_extract")
//    private boolean isValidForExtract;
//    @Column(name = "not_valid_for_extract_reason")
//    private String notValidForExtractReason;
//    @Column(name = "pure_premium")
//    private BigDecimal purePremium;
//    @Column(name = "exposure_tiv")
//    private Double exposureTiv;
//    @Column(name = "geo_code")
//    private String geoCode;
//    @Column(name = "geo_description")
//    private String geoDescription;
//    @Column(name = "user1")
//    private String user1;
//    @Column(name = "user2")
//    private String user2;
//    @Column(name = "user3")
//    private String user3;
//    @Column(name = "user4")
//    private String user4;
//    @Column(name = "region_peril_code")
//    private String regionPerilCode;
//
//
//}
