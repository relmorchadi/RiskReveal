package com.scor.rr.domain.entities.rms;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RmsAnalysisELT database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RmsAnalysisELT")
@Data
public class RmsAnalysisELT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RmsAnalysisELTId")
    private Long rmsAnalysisELTId;
    @Column(name = "RDMId")
    private Long rdmId;
    @Column(name = "RDMName")
    private String rdmName;
    @Column(name = "AnalysisId")
    private String analysisId;
    @Column(name = "InstanceId")
    private String instanceId;
    @Column(name = "FinancialPerspective")
    private String financialPerspective;
    @OneToMany(mappedBy = "rmsAnalysisELT")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RMSELTLoss> eltLosses;

    public RmsAnalysisELT() {
    }

    public RmsAnalysisELT(Long rdmId, String rdmName, String analysisId, String instanceId, String financialPerspective,
                          List<RMSELTLoss> eltLosses) {
        this.rdmId = rdmId;
        this.rdmName = rdmName;
        this.analysisId = analysisId;
        this.instanceId = instanceId;
        this.financialPerspective = financialPerspective;
        this.eltLosses = eltLosses;
    }
}
