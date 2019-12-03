package com.scor.rr.domain.riskLink;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RLSourceEPHeader")
@Data
@NoArgsConstructor
public class RLSourceEpHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rLSourceEPHeaderId;
    private Long rLAnalysisId;
    private String financialPerpective;
    private Double oEP10;
    private Double oEP50;
    private Double oEP100;
    private Double oEP250;
    private Double oEP500;
    private Double oEP1000;
    private Double aEP10;
    private Double aEP50;
    private Double aEP100;
    private Double aEP250;
    private Double aEP500;
    private Double aEP1000;
    private Double purePremium;
    private Double stdDev;
    private Double coV;

}
