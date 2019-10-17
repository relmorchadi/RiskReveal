package com.scor.rr.domain.entities.rms.exposuresummary;

import com.scor.rr.domain.entities.rms.exposuresummary.axis.AxisDefinition;
import com.scor.rr.domain.enums.ExposureSummaryDisplayStyle;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ExposureSummaryDefinition database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ExposureSummaryDefinition")
@Data
public class ExposureSummaryDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExposureSummaryDefinitionId")
    private Long exposureSummaryDefinitionId;
    @Column(name = "ExposureSummaryAlias")
    private String exposureSummaryAlias;
    @Column(name = "ExposureSummaryLabel")
    private String exposureSummaryLabel;
    @Column(name = "Enabled")
    private Boolean enabled;
    @Column(name = "Drillable")
    private Boolean drillable;
    @Column(name = "IsDisplayable")
    private Boolean isDisplayable;
    @Column(name = "IsExportable")
    private Boolean isExportable;
    @Column(name = "IsNavigation")
    private Boolean isNavigation;
    @Column(name = "ExposureSummaryOrder")
    private Integer exposureSummaryOrder;
    @Column(name = "ExposureSummaryDisplayStyle")
    private ExposureSummaryDisplayStyle exposureSummaryDisplayStyle;
    @OneToMany(mappedBy = "exposureSummaryDefinition")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AxisDefinition> axisDefinitions;
}
