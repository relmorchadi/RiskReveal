package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.references.RegionPerilGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the SourceCountryPerilRegionPerilGroupMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceCountryPerilRegionPerilGroupMapping")
public class SourceCountryPerilRegionPerilGroupMapping extends AbstractRegionPerilMapping<RegionPerilGroup> {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "SourceCountryCode2")
    private String sourceCountryCode2;
    @Column(name = "SourceCountryCode3")
    private String sourceCountryCode3;

    public SourceCountryPerilRegionPerilGroupMapping() {
        super();
    }

}
