package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.references.omega.*;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the Section database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Section")
@Data
public class Section {
    @Id
    @Column(name = "TreatySectionId")
    private String treatySectionId;
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
    @Column(name = "SectionLabel")
    private String sectionLabel;
    @Column(name = "CededShare")
    private Double cededShare;
    @Column(name = "ScorWrittenShareOfCededShare")
    private Double scorWrittenShareOfCededShare;
    @Column(name = "ScorSignedShareOfCededShare")
    private Double scorSignedShareOfCededShare;
    @Column(name = "ScorExpectedShareOfCededShare")
    private Double scorExpectedShareOfCededShare;
    @Column(name = "Earthquake")
    private Boolean earthquake;
    @Column(name = "Windstorm")
    private Boolean windstorm;
    @Column(name = "Flood")
    private Boolean flood;
    @Column(name = "ScorEGPI")
    private Double scorEGPI;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NatureId")
    private Nature nature;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractId")
    private Contract contract;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOBId")
    private LineOfBusiness lineOfBusiness;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPICurrencyId")
    private Currency eGPICurrency;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOPId")
    private TypeOfPolicy typeOfPolicy;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LiabilityCurrencyId")
    private Currency liabilityCurrency;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOBId")
    private ScopeOfBusiness scopeOfBusiness;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionStatusId")
    private TreatySectionStatus treatySectionStatus;
}
