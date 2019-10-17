package com.scor.rr.domain.entities.references.plt;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the FinancialPerspectiveType database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "FinancialPerspectiveType")
@Data
public class FinancialPerspectiveType {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;
}
