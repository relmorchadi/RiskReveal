package com.scor.rr.domain.entities.references.omega;

import com.scor.rr.domain.entities.omega.Section;
import com.scor.rr.domain.entities.omega.UWPeril;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the CountryPeril database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CountryPeril")
@Data
public class CountryPeril {
    @Id
    @Column(name = "CountryPerilId")
    private Long countryPerilId;
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "EndorsmentNumber")
    private Integer endorsmentNumber;
    @Column(name = "SectionId")
    private Integer sectionId;
    @Column(name = "InclusionId")
    private String inclusionId;
    @Column(name = "Type")
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionId")
    private Section section;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId")
    private Country country;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UWPerilId")
    private UWPeril uwPeril;
}
