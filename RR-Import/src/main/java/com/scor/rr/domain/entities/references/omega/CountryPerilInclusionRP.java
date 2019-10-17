package com.scor.rr.domain.entities.references.omega;

import com.scor.rr.domain.entities.omega.Section;
import com.scor.rr.domain.entities.omega.UWPeril;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the CountryPerilInclusionRP database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CountryPerilInclusionRP")
@Data
public class CountryPerilInclusionRP {
    @Id
    @Column(name = "CountryPerilInclusionRPId")
    private Long countryPerilInclusionRPId;
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "InclusionId")
    private Long inclusionId;
    @Column(name = "RegionPerilId")
    private Integer regionPerilId;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "IsMinimumGrainRegionPeril")
    private Boolean isMinimumGrainRegionPeril;
    @Column(name = "ParentMinimumGrainRegionPeril")
    private String parentMinimumGrainRegionPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UWPerilId")
    private UWPeril uwPeril;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId")
    private Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatSectionId")
    private Section section;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryPerilId")
    private CountryPeril countryPeril;
}
