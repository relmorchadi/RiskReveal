package com.scor.rr.domain;

import com.scor.rr.domain.enums.AxisConformerMode;
import com.scor.rr.domain.enums.AxisName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AxisConformerDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long axisConformerDefinitionId;
    @Column(name = "SourceAxis")
    @Enumerated(EnumType.STRING)
    private AxisName sourceAxis;
    @Column(name = "TargetAxis")
    @Enumerated(EnumType.STRING)
    private AxisName targetAxis;
    @Column(name = "AxisConformerMode")
    @Enumerated(EnumType.STRING)
    private AxisConformerMode axisConformerMode;
    @Column(name = "AxisConformerAlias")
    private String axisConformerAlias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exposureViewDefinitionId")
    private ExposureViewDefinition exposureViewDefinition;
}
