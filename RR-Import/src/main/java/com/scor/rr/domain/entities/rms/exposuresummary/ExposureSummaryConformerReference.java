package com.scor.rr.domain.entities.rms.exposuresummary;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ExposureSummaryConformerReference database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ExposureSummaryConformerReference")
@Data
public class ExposureSummaryConformerReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
