package com.scor.rr.domain;

import lombok.Data;

import javax.persistence.*;

@Data
public class RRFinancialPerspective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    private String fullCode;
    // FinancialPerspective
    private String modellingVendor;

    private String modellingSystem;

    private String modellingSystemVersion;

    public RRFinancialPerspective(String modellingVendor, String modellingSystem, String modellingSystemVersion, String code) {
        this.modellingVendor = modellingVendor;
        this.modellingSystem = modellingSystem;
        this.modellingSystemVersion = modellingSystemVersion;
        this.code = code;
    }
    // TODO : review the rest with shaun and viet
}
