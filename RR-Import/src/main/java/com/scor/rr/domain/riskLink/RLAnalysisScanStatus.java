package com.scor.rr.domain.riskLink;


import lombok.*;

import javax.persistence.*;
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
    @Column(name = "ScanLevel")
    private Integer scanLevel;
    @Column(name = "ScanStatus")
    private Integer scanStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastScan")
    private Date lastScan;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "RLModelAnalysisId")
    private RLAnalysis rlAnalysis;

    public RLAnalysisScanStatus(RLAnalysis rlAnalysisId, int scanStatus) {
        this.entity = 1;
        this.rlAnalysis = rlAnalysisId;
        this.scanLevel = 0; //TODO: define scan level enum
        this.scanStatus = scanStatus; //TODO: define scan status enum
        this.lastScan = new Date();
    }

}
