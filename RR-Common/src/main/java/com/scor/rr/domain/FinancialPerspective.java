package com.scor.rr.domain;


import lombok.Data;

import javax.persistence.*;

/**
 * Financial Perspective Reference Table
 */
@Entity
@Data
public class FinancialPerspective {

    @Id
    @Column(name = "FinancialPerspectiveId")
    private String financialPerspectiveId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;
    @Column(name = "Code")
    private String code;
    @Column(name = "TreatyLabel")
    /**
     * ONLY USEABLE FOR 'TY' financial Perspective;
     */
    private String treatyLabel;
    @Column(name = "TreatyId")
    private Integer treatyId;
    @Column(name = "Description")
    private String description;
    @Column(name = "UserSelectableForElt")
    private boolean userSelectableForElt;


    public String getFullCode() {
        StringBuilder builder = new StringBuilder(code);
        if (treatyId != null) {
            builder.append("-").append(treatyId);
        }
        return builder.toString();
    }
}
