package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the SubsidiaryLedger database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SubsidiaryLedger")
@Data
public class SubsidiaryLedger {
    @Id
    @Column(name = "SubsidiaryLedgerId")
    private String subsidiaryLedgerId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String label;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubsidiaryId")
    private Subsidiary subsidiary;
}
