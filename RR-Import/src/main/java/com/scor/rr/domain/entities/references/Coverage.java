package com.scor.rr.domain.entities.references;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the Coverage database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Coverage")
@Data
public class Coverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CoverageId")
    private Long coverageId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;
}
