package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "DefaultReturnPeriod")
@AllArgsConstructor
@NoArgsConstructor
public class DefaultReturnPeriodEntity {

    @Id
    @Column(name = "DefaultReturnPeriodId")
    private Long defaultReturnPeriodId;
    @Column(name = "ReturnPeriod")
    private Integer returnPeriod;
    @Column(name = "ExceedanceProbability")
    private Double exceedanceProbability;
    @Column(name = "IsTableRP")
    private Boolean isTableRP;
    @Column(name = "IsCurveEP")
    private Boolean isCurveEP;

}
