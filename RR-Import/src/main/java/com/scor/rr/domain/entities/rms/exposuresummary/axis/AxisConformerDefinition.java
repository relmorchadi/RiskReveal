package com.scor.rr.domain.entities.rms.exposuresummary.axis;

import com.scor.rr.domain.entities.rms.exposuresummary.SystemExposureSummaryDefinition;
import com.scor.rr.domain.enums.AxisConformerMode;
import com.scor.rr.domain.enums.AxisName;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the AxisConformerDefinition database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AxisConformerDefinition")
@Data
public class AxisConformerDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AxisConformerDefinitionId")
    private Long axisConformerDefinitionId;
    @Column(name = "AxisConformerAlias")
    private String axisConformerAlias;
    @Column(name = "SourceAxis")
    private AxisName sourceAxis;
    @Column(name = "TargetAxis")
    private AxisName targetAxis;
    @Column(name = "AxisConformerMode")
    private AxisConformerMode axisConformerMode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SystemExposureSummaryDefinitionId")
    private SystemExposureSummaryDefinition systemExposureSummaryDefinition;
}
