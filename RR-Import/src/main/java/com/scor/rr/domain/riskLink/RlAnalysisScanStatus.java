package com.scor.rr.domain.riskLink;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


@Data
@Getter
@Setter
@Entity
@Table(name = "RLAnalysisScanStatus")
public class RlAnalysisScanStatus {

    @Id
    private Integer RLAnalysisScanStatusId;
    private Integer entity;
    private BigInteger rlAnalysisId;
    private Integer scanLevel;
    private Integer scanStatus;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastScan;

    public RlAnalysisScanStatus(BigInteger rlAnalysisId, int scanStatus) {
        this.entity = 1;
        this.rlAnalysisId = rlAnalysisId;
        this.scanLevel = 0; //TODO: define scan level enum
        this.scanStatus = scanStatus; //TODO: define scan status enum
        this.lastScan = new Date();
    }

}
