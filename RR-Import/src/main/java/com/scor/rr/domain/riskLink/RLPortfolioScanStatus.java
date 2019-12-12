package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.enums.ScanLevelEnum;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RLPortfolioScanStatusId")
    private Long rlPortfolioScanStatusId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "ScanLevel")
    private ScanLevelEnum scanLevel;
    @Column(name = "ScanStatus")
    private Integer scanStatus;
    @Column(name = "LastScan")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastScan;
    @Column(name = "RLPortfolio")
    private Long rlPortfolio;

    public RLPortfolioScanStatus(RLPortfolio rlPortfolio, int scanStatus) {
        this.entity = 1;
        this.rlPortfolio = rlPortfolio.getRlPortfolioId();
        this.scanStatus = scanStatus;
        this.scanLevel = ScanLevelEnum.get(scanStatus);
    }
}
