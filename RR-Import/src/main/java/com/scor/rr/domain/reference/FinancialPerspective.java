package com.scor.rr.domain.reference;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Financial Perspective Reference Table
 */
@Entity
@Data
public class FinancialPerspective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long financialPerspectiveId;

    private String modellingVendor;

    private String modellingSystem;

    private String modellingSystemVersion;

    private String code;

    /**
     * ONLY USEABLE FOR 'TY' financial Perspective;
     */
    private String treatyLabel;

    private Integer treatyId;

    private String description;

    private boolean userSelectableForElt;


    public String getFullCode() {
        StringBuilder builder = new StringBuilder(code);
        if (treatyId != null) {
            builder.append("-").append(treatyId);
        }
        return builder.toString();
    }
}
