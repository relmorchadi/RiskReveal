package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.workspace.Project;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the RmsPortfolioBasic database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsPortfolioBasic")
@Data
public class RmsPortfolioBasic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RmsPortfolioBasicId")
    private Long rmsPortfolioBasicId;
    @Column(name = "EDMId")
    private Long edmId;
    @Column(name = "EDMName")
    private String edmName;
    @Column(name = "PortfolioId")
    private Long portfolioId;
    @Column(name = "Number")
    private String number;
    @Column(name = "Name")
    private String name;
    @Column(name = "Created")
    private Date created;
    @Column(name = "Description")
    private String description;
    @Column(name = "Type")
    private String type;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "AgSource")
    private String agSource;
    @Column(name = "AgCedent")
    private String agCedent;
    @Column(name = "AgCurrency")
    private String agCurrency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RmsModelDatasourceId")
    private RmsModelDatasource rmsModelDatasource;
}
