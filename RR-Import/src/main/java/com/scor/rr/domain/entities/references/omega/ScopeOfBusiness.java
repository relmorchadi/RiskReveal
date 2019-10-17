package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ScopeOfBusiness database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ScopeOfBusiness")
@Data
public class ScopeOfBusiness {
    @Id
    @Column(name = "SOBId")
    private Long sobId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Label")
    private String label;
}
