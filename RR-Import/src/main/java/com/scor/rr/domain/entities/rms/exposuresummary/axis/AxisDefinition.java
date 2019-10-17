package com.scor.rr.domain.entities.rms.exposuresummary.axis;

import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummaryDefinition;
import com.scor.rr.domain.entities.rms.exposuresummary.SystemExposureSummaryDefinition;
import com.scor.rr.domain.enums.AxisDisplayStyle;
import com.scor.rr.domain.enums.AxisName;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the AxisDefinition database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AxisDefinition")
@Data
public class AxisDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AxisDefinitionId")
    private Long axisDefinitionId;
    @Column(name = "Label")
    private String label;
    @Column(name = "Displayable")
    private Boolean displayable;
    @Column(name = "AxisOrder")
    private Integer axisOrder;
    @Column(name = "Name")
    private AxisName name;
    @Column(name = "AxisDisplayStyle")
    private AxisDisplayStyle axisDisplayStyle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SystemExposureSummaryDefinitionId")
    private SystemExposureSummaryDefinition systemExposureSummaryDefinition;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureSummaryDefinitionId")
    private ExposureSummaryDefinition exposureSummaryDefinition;
}
