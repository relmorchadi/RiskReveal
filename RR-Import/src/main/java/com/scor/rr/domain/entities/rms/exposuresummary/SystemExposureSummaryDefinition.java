package com.scor.rr.domain.entities.rms.exposuresummary;

import com.scor.rr.domain.entities.rms.exposuresummary.axis.AxisConformerDefinition;
import com.scor.rr.domain.entities.rms.exposuresummary.axis.AxisDefinition;
import com.scor.rr.domain.enums.ExposureSummaryType;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the SystemExposureSummaryDefinition database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SystemExposureSummaryDefinition")
@Data
public class SystemExposureSummaryDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SystemExposureSummaryDefinitionId")
    private Long systemExposureSummaryDefinitionId;
    @Column(name = "SourceVendor")
    private String sourceVendor;
    @Column(name = "SourceSystem")
    private String sourceSystem;
    @Column(name = "Version")
    private String version;
    @Column(name = "ExposureSummaryAlias")
    private String exposureSummaryAlias;
    @Column(name = "ExposureSummaryExtractionProcedure")
    private String exposureSummaryExtractionProcedure;
    @Column(name = "BasedOnSummaryAlias")
    private String basedOnSummaryAlias;
    @Column(name = "ConditionExpression")
    private String conditionExpression;
    @Column(name = "ExposureSummaryType")
    private ExposureSummaryType exposureSummaryType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureSummaryDefinitionId")
    private ExposureSummaryDefinition exposureSummaryDefinition;
    @OneToMany(mappedBy = "systemExposureSummaryDefinition")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AxisDefinition> axisDefinitions;
    @OneToMany(mappedBy = "systemExposureSummaryDefinition")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AxisConformerDefinition> exposureSummaryAxisDefinitions;
}
