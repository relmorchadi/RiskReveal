package com.scor.rr.domain.reference;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExposureSummaryConformerReference {

    @Id
    @Column(name = "ExposureSummaryConformerReferenceId")
    private Long exposureSummaryConformerReferenceId;

    @Column(name = "SourceVendor")
    private String sourceVendor;

    @Column(name = "SourceSystem")
    private String sourceSystem;

    @Column(name = "Version")
    private String version;

    @Column(name = "AxisConformerAlias")
    private String axisConformerAlias;

    @Column(name = "InputCode")
    private String inputCode;

    @Column(name = "OutputCode")
    private String outputCode;

    @Column(name = "SortOrder")
    private Integer sortOrder;
}