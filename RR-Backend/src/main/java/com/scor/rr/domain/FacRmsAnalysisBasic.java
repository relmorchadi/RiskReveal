package com.scor.rr.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class FacRmsAnalysisBasic {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "rdm_id")
    private Integer rdmId;
    @Column(name = "rdm_name")
    private String rdmName;
    @Column(name = "analysis_id")
    private int analysisId;
    @Column(name = "analysis_name")
    private String analysisName;
    @Column(name = "analysis_description")
    private String analysisDescription;
    @Column(name = "engine_version")
    private String engineVersion;
    @Column(name = "grouptype_name")
    private String grouptypeName;
    @Column(name = "cedant")
    private String cedant;
    @Column(name = "lob_name")
    private String lobName;
    @Column(name = "is_grouping")
    private int isGrouping;
    @Column(name = "engine_type")
    private String engineType;
    @Column(name = "run_date")
    private String runDate;
    @Column(name = "type_name")
    private String typeName;
    @Column(name = "peril")
    private String peril;
    @Column(name = "sub_peril")
    private String subPeril;
    @Column(name = "loss_amplification")
    private String lossAmplification;
    @Column(name = "region")
    private String region;
    @Column(name = "region_name")
    private String regionName;
    @Column(name = "mode_name")
    private String modeName;
    @Column(name = "user1")
    private String user1;
    @Column(name = "user2")
    private String user2;
    @Column(name = "user3")
    private String user3;
    @Column(name = "user4")
    private String user4;
    @Column(name = "analysis_ccy")
    private String analysisCcy;
    @Column(name = "status_description")
    private String statusDescription;

}
