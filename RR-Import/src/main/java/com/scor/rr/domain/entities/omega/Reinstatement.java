package com.scor.rr.domain.entities.omega;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the Reinstatement database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Reinstatement")
@Data
public class Reinstatement {
    @Id
    @Column(name = "ReinstatementId")
    private Long reinstatementId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "EndorsementNum")
    private Integer endorsementNum;
    @Column(name = "SectionId")
    private Integer sectionId;
    @Column(name = "Rank")
    private Integer rank;
    @Column(name = "ReinstatementLabel")
    private String reinstatementLabel;
    @Column(name = "Premium")
    private Double premium;
    @Column(name = "ProportionCededRate")
    private Double proportionCededRate;
    @Column(name = "ProrataTemporisYN")
    private Boolean prorataTemporisYN;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionId")
    private Section section;
}
