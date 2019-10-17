package com.scor.rr.domain.entities.references.plt;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the FinancialPerspective database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "FinancialPerspective")
@Data
public class FinancialPerspective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;
    @Column(name = "IsDefaultFacELT")
    private Boolean isDefaultFacELT;
    @Column(name = "IsDefaultFacStats")
    private Boolean isDefaultFacStats;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FinancialPerspectiveTypeId")
    private FinancialPerspectiveType type;
}
