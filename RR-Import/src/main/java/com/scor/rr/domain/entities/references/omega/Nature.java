package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the Nature database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Nature")
@Data
public class Nature {

    @Id
    @Column(name = "NatureId")
    private Long natureId;
    @Column(name = "NatureCode")
    private String natureCode;
    @Column(name = "Label")
    private String label;
    @Column(name = "Mnemonic")
    private String mnemonic;
    @Column(name = "GeneralNature")
    private String generalNature;
    @Column(name = "FamilyCode")
    private String familyCode;
    @Column(name = "FamilyLabel")
    private String familyLabel;
}
