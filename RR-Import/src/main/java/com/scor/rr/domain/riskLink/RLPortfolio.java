package com.scor.rr.domain.riskLink;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.scor.rr.domain.EdmPortfolioBasic;
import com.scor.rr.domain.enums.ScanLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ZZ_RLPortfolio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLPortfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RLPortfolioId")
    private Long rlPortfolioId;
    @Column(name = "Entity")
    private Long entity;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "EDMId")
    private Long edmId;
    @Column(name = "EDMName")
    private String edmName;
    @Column(name = "PortfolioId")
    private Long rlId;
    @Column(name = "RLPortfolioName")
    private String name;
    @Column(name = "RLPortfolioNumber")
    private String number;
    @Column(name = "Created")
    private Date created;
    @Column(name = "Description")
    private String description;
    @Column(name = "DescriptionType")
    private String descriptionType;
    @Column(name = "Peril")
    private String peril;
    @Column(name = "AgSource")
    private String agSource;
    @Column(name = "AgCedent")
    private String agCedent;
    @Column(name = "AgCurrency")
    private String agCurrency;
    @Column(name = "Type")
    private String type;
    @Column(name = "TIV")
    private BigDecimal tiv;

    @Column(name = "ScanLevel")
    private ScanLevelEnum scanLevel;
    @Column(name = "ScanStatus")
    private Integer scanStatus;
    @Column(name = "LastScan")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastScan;

    @OneToMany(mappedBy = "rlPortfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<RLPortfolioAnalysisRegion> rlPortfolioAnalysisRegions;



    @ManyToOne
    @JoinColumn(name = "RLDataSourceId")
    @JsonBackReference
    private RLModelDataSource rlModelDataSource;



    public RLPortfolio(EdmPortfolioBasic edmPortfolioBasic, RLModelDataSource edm,int scanStatus) {
        this.entity = 1L;
        this.projectId = edm.getProjectId();
        this.edmId = edm.getRlId();
        this.edmName = edm.getName();
        this.rlId = edmPortfolioBasic.getPortfolioId();
        this.name = edmPortfolioBasic.getName();
        try {
            this.created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(edmPortfolioBasic.getCreated());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.number = edmPortfolioBasic.getNumber();
        this.description = edmPortfolioBasic.getDescription();
        this.type = edmPortfolioBasic.getType();
        this.peril = edmPortfolioBasic.getPeril();
        this.agSource = edmPortfolioBasic.getAgSource();
        this.agCedent = edmPortfolioBasic.getAgCedant();
        this.agCurrency = edmPortfolioBasic.getAgCurrency();
        this.rlModelDataSource = edm;
        this.scanStatus = scanStatus;
        this.scanLevel = ScanLevelEnum.get(scanStatus);
    }
}
