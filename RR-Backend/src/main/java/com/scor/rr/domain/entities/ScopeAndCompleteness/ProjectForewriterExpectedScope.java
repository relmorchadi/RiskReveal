package com.scor.rr.domain.entities.ScopeAndCompleteness;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="ProjectForewriterExpectedScope")
public class ProjectForewriterExpectedScope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectForewriterExpectedScopeId", nullable = false)
    private String projectForewriterExpectedScopeId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "FACNumber")
    private String fACNumber;

    @Column(name = "UWYear")
    private int uWYear;

    @Column(name = "EndorsementNumber")
    private int endorsementNumber;

    @Column(name = "UWOrder")
    private int uWOrder;

    @Column(name = "SourceAnalysisName")
    private String sourceAnalysisName;

    @Column(name = "DivisionNumber")
    private int divisionNumber;

    @Column(name = "Country")
    private String country;

    @Column(name = "State")
    private String state;

    @Column(name = "TIV")
    private BigDecimal tIV;

    @Column(name = "TIVCurrency")
    private String tIVCurrency;

    @Column(name = "Perils")
    private String perils;

    public ProjectForewriterExpectedScope() {
    }
}
