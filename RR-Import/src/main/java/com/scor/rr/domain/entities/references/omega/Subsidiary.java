package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the Subsidiary database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Subsidiary")
@Data
public class Subsidiary {
    @Id
    @Column(name = "SubsidiaryId")
    private String subsidiaryId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String label;
}
