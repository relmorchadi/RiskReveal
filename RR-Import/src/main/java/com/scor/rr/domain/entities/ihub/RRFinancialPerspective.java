package com.scor.rr.domain.entities.ihub;

import com.scor.rr.domain.entities.references.plt.TTFinancialPerspective;
import com.scor.rr.domain.entities.rms.AnalysisFinancialPerspective;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the RRFinancialPerspective database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRFinancialPerspective")
@Data
public class RRFinancialPerspective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RRFinancialPerspectiveId")
    private Long rrFinancialPerspectiveId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "OccurenceBasisOverridenBy")
    private String occurenceBasisOverridenBy;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;
    @Column(name = "Description")
    private String description;
    @Column(name = "IsUserSelectableForElt")
    private Boolean userSelectableForElt;
    @Column(name = "Code")
    private String code;
    @Column(name = "TreatyLabel")
    private String treatyLabel;
    @Column(name = "TreatyId")
    private Integer treatyId;
    @Column(name = "FullCode")
    private String fullCode;
    @Column(name = "TreatyType")
    private String treatyType;
    @Column(name = "DefaultOccurrenceBasis")
    private String defaultOccurrenceBasis;
    @Column(name = "UserOccurrenceBasis")
    private String userOccurrenceBasis;
    @Column(name = "OccurrenceBasisOverrideReason")
    private String occurrenceBasisOverrideReason;
    //@Column(name = "AnalysisFinancialPerspective")
    @OneToOne
    private AnalysisFinancialPerspective analysisFinancialPerspective;


    public RRFinancialPerspective(TTFinancialPerspective financialPerspective) {
        this.modellingVendor = financialPerspective.getModellingVendor();
        this.modellingSystem = financialPerspective.getModellingSystem();
        this.modellingSystemVersion = financialPerspective.getModellingSystemVersion();
        this.code = financialPerspective.getCode();
        this.treatyLabel = financialPerspective.getTreatyLabel();
        this.treatyId = financialPerspective.getTreatyId();
        this.description = financialPerspective.getDescription();
        this.userSelectableForElt = financialPerspective.isUserSelectableForElt();
    }

    public RRFinancialPerspective(AnalysisFinancialPerspective analysisFinancialPerspective) {
        this.code = analysisFinancialPerspective.getCode();
        this.treatyLabel = analysisFinancialPerspective.getTreatyLabel();
        this.treatyId = analysisFinancialPerspective.getTreatyId();
        this.fullCode = analysisFinancialPerspective.getFullCode();
        this.treatyType = analysisFinancialPerspective.getTreatyType();
        this.defaultOccurrenceBasis = analysisFinancialPerspective.getDefaultOccurrenceBasis();
        this.userOccurrenceBasis = analysisFinancialPerspective.getUserOccurrenceBasis();
        this.occurrenceBasisOverrideReason = analysisFinancialPerspective.getOccurrenceBasisOverrideReason();
        this.occurenceBasisOverridenBy = analysisFinancialPerspective.getOccurenceBasisOverridenBy();
    }
}
