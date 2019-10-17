package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the DefaultReturnPeriod database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "DefaultReturnPeriod")
@Data
public class DefaultReturnPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DefaultReturnPeriodId")
    private Long defaultReturnPeriodId;
    @Column(name = "ReturnPeriod")
    private Integer returnPeriod;
    @Column(name = "ExcedanceProbability")
    private Double excedanceProbability;
    @Column(name = "IsTableRP")
    private Boolean isTableRP;
    @Column(name = "IsCurveEP")
    private Boolean isCurveEP;
}
