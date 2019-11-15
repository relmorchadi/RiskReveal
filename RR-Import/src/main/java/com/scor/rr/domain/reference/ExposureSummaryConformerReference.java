package com.scor.rr.domain.reference;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class ExposureSummaryConformerReference {

    @Id
    private Long exposureSummaryConformerReferenceId;

    private String sourceVendor;

    private String sourceSystem;

    private String version;

    private String axisConformerAlias;

    private String inputCode;

    private String outputCode;

    private Integer sortOrder;
}