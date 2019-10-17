package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.enums.ExpectedFrequency;
import com.scor.rr.domain.enums.FinacialShareBasis;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the MGA database table
 *
 * @author HADDINI Zakariyae  && HAMIANI Mohammed
 */
@Entity
@Table(name = "MGA")
@Data
public class MGA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "SectionId")
    private Integer sectionId;
    @Column(name = "SubmissionPeriod")
    private String submissionPeriod;
    @Column(name = "FinancialBasis")
    private FinacialShareBasis financialBasis;
    @Column(name = "ExpectedFrequency")
    private ExpectedFrequency expectedFrequency;
}
