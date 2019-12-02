package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "ExposureViewDefinition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureViewDefinition {

    @Id
    @Column(name = "ExposureViewDefinitionId")
    private Long exposureViewDefinitionId;
    @Column(name = "Name")
    private String name;
    @Column(name = "OrderDefinition")
    private Integer order;
    @Column(name="ExposureSummaryType")
    private String exposureSummaryType;
    @Column(name="ExposureSummaryAlias")
    private String exposureSummaryAlias;
    @Column(name="BasedOnSummaryAlias")
    private String basedOnSummaryAlias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureViewId")
    private ExposureView exposureView;

    @OneToMany(mappedBy = "exposureViewDefinition", fetch = FetchType.EAGER)
    private List<AxisConformerDefinition> axisConformerDefinitions;

    @OneToMany(mappedBy = "exposureViewDefinition", fetch = FetchType.LAZY)
    private List<ExposureViewVersion> exposureViewVersions;
}
