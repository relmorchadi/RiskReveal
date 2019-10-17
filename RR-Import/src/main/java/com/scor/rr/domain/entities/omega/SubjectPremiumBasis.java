package com.scor.rr.domain.entities.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the SubjectPremiumBasis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SubjectPremiumBasis")
@Data
public class SubjectPremiumBasis {
    @Id
    @Column(name = "SubjectPremiumBasisId")
    private Long subjectPremiumBasisId;
    @Column(name = "Label")
    private String label;
    @Column(name = "LongName")
    private String longName;
}
