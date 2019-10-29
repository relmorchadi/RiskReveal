package com.scor.rr.domain.reference;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;


@Data
@Entity
public class Contract {

    @Id
    private Long id;

    private String description;
    private String contractSourceTypeName;
    private String treatyId;
    private Integer uwYear;
    private Integer uwOrder;
    private Integer endorsementNumber;
    private String treatyLabel;
    /** @TODO => Review any attributes to Spread
    private TreatySectionStatus treatySectionStatus;
    private Subsidiary subsidiary;
    private UWUnit uWUnit;
    private SubsidiaryLedger subsidiaryLedger;
    private Date inceptionDate;
    private ContractSourceType contractSourceType;
     */
    /** Spreaded Attr **/
    private String contractSourceTypeId;
    /** Spreaded Attr **/
    private Date expiryDate;
    private String programId;
    private String programName;
    private String bouquetId;
    private String bouquetName;
    // private Client client;
    private String underwriterId;
    private String underwriterFirstName;
    private String underwriterLastName;
    @ManyToOne
    private Currency liabilityCurrency;
    private String annualLimitAmmount;
    private String eventLimitAmount;
}
