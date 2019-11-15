package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureViewDefinition {

    @Id
    private Long exposureViewDefinitionId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Order")
    private Integer Order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureViewId")
    private ExposureView exposureView;

    @OneToMany(mappedBy = "exposureViewDefinition", fetch = FetchType.EAGER)
    private List<AxisConformerDefinition> axisConformerDefinitions;
}
