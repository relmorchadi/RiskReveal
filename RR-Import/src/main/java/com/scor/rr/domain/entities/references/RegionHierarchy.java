package com.scor.rr.domain.entities.references;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the RegionHierarchy database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RegionHierarchy")
@Data
public class RegionHierarchy {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
    @Column(name = "IsAdmin")
    private Boolean admin;
    @Column(name = "Country")
    private Boolean country;
    @Column(name = "CATRegion")
    private Boolean catRegion;
    @Column(name = "BroadRegion")
    private Boolean broadRegion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MacroRegionId")
    private MacroRegion macroRegion;

}
