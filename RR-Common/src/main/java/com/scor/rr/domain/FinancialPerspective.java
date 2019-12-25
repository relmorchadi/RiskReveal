package com.scor.rr.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Financial Perspective Reference Table
 */
@Entity
@Data
public class FinancialPerspective {

    @Id
    @Column(name = "FinancialPerspectiveId")
    private Long financialPerspectiveId;
    @Column(name = "Code")
    private String code;
    //    @Column(name = "ModellingVendor")
//    private String modellingVendor;
//    @Column(name = "ModellingSystem")
//    private String modellingSystem;
//    @Column(name = "ModellingSystemVersion")
//    private String modellingSystemVersion;

//    @Column(name = "TreatyLabel")
//    /**
//     * ONLY USEABLE FOR 'TY' financial Perspective;
//     */
//    private String treatyLabel;
//    @Column(name = "TreatyId")
//    private Integer treatyId;
    @Column(name = "Description")
    private String description;
    @Column(name = "UserSelectableForElt")
    private boolean userSelectableForElt;

}
