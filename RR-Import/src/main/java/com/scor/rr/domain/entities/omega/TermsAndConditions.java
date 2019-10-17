package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.references.omega.Currency;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the TermsAndConditions database table
 *
 * @author HADDINI  && HAMIANI Mohammed
 */
@Entity
@Table(name = "TermsAndConditions")
@Data
public class TermsAndConditions {
    @Id
    @Column(name = "TermsAndConditionsId")
    private Long termsAndConditionsId;
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "EndorsementNum")
    private Integer endorsementNum;
    @Column(name = "SectionId")
    private Integer sectionId;
    @Column(name = "OccurenceBasis")
    private String occurenceBasis;
    @Column(name = "EstimatedSubjectPremium")
    private Double estimatedSubjectPremium;
    @Column(name = "IsUnlimited")
    private Boolean isUnlimited;
    @Column(name = "CededOr100PctShare")
    private String cededOr100PctShare;
    @Column(name = "EventLimit")
    private Double eventLimit;
    @Column(name = "AnnualLimit")
    private Double annualLimit;
    @Column(name = "AnnualDeductible")
    private Double annualDeductible;
    @Column(name = "EventLimitForEQ")
    private Double eventLimitForEq;
    @Column(name = "EventLimitForWS")
    private Double eventLimitForWs;
    @Column(name = "EventLimitForFL")
    private Double eventLimitForFl;
    @Column(name = "Capacity")
    private Double capacity;
    @Column(name = "AttachmentPoint")
    private Double attachmentPoint;
    @Column(name = "AggregateDeductible")
    private Double aggregateDeductible;
    @Column(name = "UnlimitedReinstatementYN")
    private Boolean unlimitedReinstatementYN;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionId")
    private Section section;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrencyId")
    private Currency subjectPremiumCurrency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectPremiumBasisId")
    private SubjectPremiumBasis subjectPremiumBasis;
}
