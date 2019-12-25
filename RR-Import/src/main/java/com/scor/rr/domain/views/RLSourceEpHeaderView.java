package com.scor.rr.domain.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_RLSourceEpHeader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLSourceEpHeaderView {

    @Id
    @Column(name = "")
    private Long id;
    @Column(name = "RLAnalysisId")
    private Long rLAnalysisId;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @Column(name = "FpDesc")
    private String fpDesc;
    @Column(name = "OEP10")
    private Double oEP10;
    @Column(name = "OEP50")
    private Double oEP50;
    @Column(name = "AEP10")
    private Double aEP10;
    @Column(name = "AEP50")
    private Double aEP50;
    @Column(name = "PurePremium")
    private Double purePremium;
    @Column(name = "StdDev")
    private Double stdDev;
    @Column(name = "CoV")
    private Double coV;
}
