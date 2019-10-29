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
public class RlAnalysisScanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer RLAnalysisScanStatusId;
    private Integer entity;
    private Long rlAnalysisId;
    private Integer scanLevel;
    private Integer scanStatus;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastScan;

    public RlAnalysisScanStatus(Long rlAnalysisId, int scanStatus) {
        this.entity = 1;
        this.rlAnalysisId = rlAnalysisId;
        this.scanLevel = 0; //TODO: define scan level enum
        this.scanStatus = scanStatus; //TODO: define scan status enum
        this.lastScan = new Date();
    }

}
