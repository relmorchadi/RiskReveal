package com.scor.rr.domain.entities.references.plt;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the TTFinancialPerspective database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "TTFinancialPerspective")
@Data
public class TTFinancialPerspective {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "TTFinancialPerspectiveId")
    private String ttFinancialPerspectiveId;
    @Column(name = "ModellingVendor")
    private String modellingVendor;
    @Column(name = "ModellingSystem")
    private String modellingSystem;
    @Column(name = "ModellingSystemVersion")
    private String modellingSystemVersion;
    @Column(name = "Code")
    private String code;
    @Column(name = "TreatyLabel")
    private String treatyLabel;
    @Column(name = "TreatyId")
    private Integer treatyId;
    @Column(name = "Description")
    private String description;
    @Column(name = "UserSelectableForElt")
    private boolean userSelectableForElt;
}
