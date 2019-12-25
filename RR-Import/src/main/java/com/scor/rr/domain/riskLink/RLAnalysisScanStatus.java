package com.scor.rr.domain.riskLink;


import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


@Data
@Entity
@Table(name = "RLAnalysisScanStatus")
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisScanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RLAnalysisScanStatusId")
    private Long RLAnalysisScanStatusId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "RLModelAnalysisId")
    private Long rlAnalysisId;
    @Column(name = "ScanLevel")
    private Integer scanLevel;
    @Column(name = "ScanStatus")
    private Integer scanStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastScan")
    private Date lastScan;

    public RLAnalysisScanStatus(Long rlAnalysisId, int scanStatus) {
        this.entity = 1;
        this.rlAnalysisId = rlAnalysisId;
        this.scanLevel = 0; //TODO: define scan level enum
        this.scanStatus = scanStatus; //TODO: define scan status enum
        this.lastScan = new Date();
    }

}
