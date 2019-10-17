package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the PeriodBasis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "PeriodBasis")
@Data
public class PeriodBasis {
    @Id
    @Column(name = "PeriodBasisId")
    private String periodBasisId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;
}
