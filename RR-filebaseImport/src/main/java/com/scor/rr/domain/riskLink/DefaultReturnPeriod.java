package com.scor.rr.domain.riskLink;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class DefaultReturnPeriod {

    @Id
    private Long defaultReturnPeriodId;
    private Integer returnPeriod;
    private Double excedanceProbability;
    private Boolean isTableRP;
    private Boolean isCurveEP;

}
