package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.plt.ModellingSystem;
import com.scor.rr.domain.entities.references.RegionPeril;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the SourceCountryPerilRegionPerilMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SourceCountryPerilRegionPerilMapping")
@Data
public class SourceCountryPerilRegionPerilMapping {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "SourcePerilCode")
    private String sourcePerilCode;
    @Column(name = "SourceCountryCode2")
    private String sourceCountryCode2;
    @Column(name = "SourceCountryCode3")
    private String sourceCountryCode3;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RegionPerilId")
    private RegionPeril regionPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemId")
    private ModellingSystem modellingSystem;
}

