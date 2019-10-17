package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ClaimsBasis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ClaimsBasis")
@Data
public class ClaimsBasis {
    @Id
    @Column(name = "ClaimsBasisId")
    private Long claimsBasisId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String desc;
}
