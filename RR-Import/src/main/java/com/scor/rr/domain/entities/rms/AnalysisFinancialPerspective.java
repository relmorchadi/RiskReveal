package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.ihub.RRFinancialPerspective;
import com.scor.rr.domain.entities.ihub.SourceResult;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;


@Entity
@Table(name = "AnalysisFinancialPerspective")
@Data
public class AnalysisFinancialPerspective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnalysisFinancialPerspectiveId")
    private Long analysisFinancialPerspectiveId;
    @Column(name = "Code")
    private String code;
    @Column(name = "TreatyLabel")
    private String treatyLabel;
    @Column(name = "TreatyTag")
    private String treatyTag;
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
    @Column(name = "OccurenceBasisOverridenBy")
    private String occurenceBasisOverridenBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceResultId")
    private SourceResult sourceResult;

    public AnalysisFinancialPerspective() {
    }

    public AnalysisFinancialPerspective(String code, String treatyLabel, Integer treatyId) {
        this.code = code;
        this.treatyLabel = treatyLabel;
        this.treatyId = treatyId;
    }

    public AnalysisFinancialPerspective(RRFinancialPerspective rrFinancialPerspective) {
        this.code = rrFinancialPerspective.getCode();
        this.treatyLabel = rrFinancialPerspective.getTreatyLabel();
        this.treatyId = rrFinancialPerspective.getTreatyId();
        this.fullCode = rrFinancialPerspective.getFullCode();
        this.treatyType = rrFinancialPerspective.getTreatyType();
        this.defaultOccurrenceBasis = rrFinancialPerspective.getDefaultOccurrenceBasis();
        this.userOccurrenceBasis = rrFinancialPerspective.getUserOccurrenceBasis();
        this.occurrenceBasisOverrideReason = rrFinancialPerspective.getOccurrenceBasisOverrideReason();
        this.occurenceBasisOverridenBy = rrFinancialPerspective.getOccurenceBasisOverridenBy();
    }

    /**
     * get Display Code
     *
     * @return
     */
    public String getDisplayCode() {
        if ("TY".equals(this.code)) {
            if (!StringUtils.isEmpty(this.treatyTag))
                return this.code + "-" + this.treatyTag;

            else if (!StringUtils.isEmpty(this.treatyLabel))
                return this.code + "-" + this.treatyLabel.replaceAll(" ", "");
        }

        return this.code;
    }

}
