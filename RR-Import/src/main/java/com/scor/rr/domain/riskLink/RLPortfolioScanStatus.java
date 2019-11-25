package com.scor.rr.domain.riskLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolioScanStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RLPortfolioScanStatusId")
    private Long rlPortfolioScanStatusId;
    @Column(name = "RREntity")
    private Integer entity;
    @Column(name = "ScanLevel")
    private Integer scanLevel;
    @Column(name = "ScanStatus")
    private Integer scanStatus;
    @Column(name = "LastScan")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastScan;

    @OneToOne
    @JoinColumn(name = "RLPortfolioId")
    private RLPortfolio rlPortfolio;
}
