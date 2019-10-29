package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.RdmAnalysis;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "RLSourceResult")
public class RlSourceResult {

    @Id
    @GeneratedValue
    @Column(name = "RlSourceResultId")
    private Integer rlSourceResultId;
    private Long rlAnalysisId;
    @Column(name = "Entity")
    private Integer entity;
    @Column(name = "projectId")
    private Long projectId;
    @Column(name = "targetCurrency")
    private String targetCurrency;
    @Column(name = "targetRegionPeril")
    private String targetRegionPeril;
    @Column(name = "overrideRegionPerilBasis")
    private String overrideRegionPerilBasis;
    @Column(name = "occurrenceBasis")
    private String occurrenceBasis;
    @Column(name = "financialPerspective")
    private String financialPerspective;
    @Column(name = "unitMultiplier")
    private Number unitMultiplier;
    @Column(name = "proportion")
    private Number proportion;
    @Column(name = "targetRAPCode")
    private String targetRAPCode;

    @ManyToOne
    @JoinColumn(name = "rlAnalysisId")
    private RLAnalysis rlAnalysis;

    public RlSourceResult(RdmAnalysis analysis, Long projectId){
        this.entity=1;
        this.projectId = projectId;
        this.targetCurrency = analysis.getAnalysisCurrency();
        //this.targetRegionPeril; // TODO: Calculate
        this.overrideRegionPerilBasis = null;
    }


}
