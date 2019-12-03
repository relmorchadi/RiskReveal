package com.scor.rr.domain.riskLink;

import com.scor.rr.domain.EdmPortfolioBasic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RlPortfolioId")
    private Long rlPortfolioId;
    @Column(name = "Entity")
    private Long entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "EdmId")
    private Long edmId;
    @Column(name = "EdmName")
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
    @Column(name = "TIV")
    private BigDecimal tiv;

    @OneToMany
    private List<RLPortfolioAnalysisRegion> rlPortfolioAnalysisRegions;

    @ManyToOne
    @JoinColumn(name = "RlModelDataSourceId")
    private RLModelDataSource rlModelDataSource;

    public RLPortfolio(EdmPortfolioBasic edmPortfolioBasic, RLModelDataSource edm) {
        this.entity = 1L;
        this.projectId = edm.getProjectId();
        this.edmId = Long.valueOf(edm.getRlId());
        this.edmName = edm.getName();
        this.portfolioId = edmPortfolioBasic.getPortfolioId();
        this.name = edmPortfolioBasic.getName();
        //this.created = edmPortfolioBasic.getCreated();
        this.description = edmPortfolioBasic.getDescription();
        this.type = edmPortfolioBasic.getType();
        this.peril = edmPortfolioBasic.getPeril();
        this.agSource = edmPortfolioBasic.getAgSource();
        this.agCedent = edmPortfolioBasic.getAgCedant();
        this.agCurrency = edmPortfolioBasic.getAgCurrency();
        this.rlModelDataSource = edm;

    }
}
