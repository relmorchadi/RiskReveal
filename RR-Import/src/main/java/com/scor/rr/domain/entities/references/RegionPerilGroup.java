package com.scor.rr.domain.entities.references;

import com.scor.rr.domain.entities.references.omega.Currency;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the RegionPerilGroup database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RegionPerilGroup")
@Data
public class RegionPerilGroup {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String description;
    @Column(name = "FACApplicable")
    private Boolean facApplicable;
    @Column(name = "PortfolioOnly")
    private Boolean portfolioOnly;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PerilId")
    private PerilGroup peril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CapacityCCY")
    private Currency capacityCCY;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionId")
    private RegionHierarchy region;
}
