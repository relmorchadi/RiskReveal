package com.scor.rr.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "FacContracts", schema = "poc")
public class FacContract {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "lastUpdateDate")
    private String lastUpdateDate;
    @Column(name = "lastUpdatedBy")
    private String lastUpdatedBy;
    @Column(name = "reauestedByFirstName")
    private String reauestedByFirstName;
    @Column(name = "reauestedByLastName")
    private String reauestedByLastName;
    @Column(name = "requestCreationDate")
    private String requestCreationDate;
    @Column(name = "uWanalysisContractBusinessType")
    private String uWanalysisContractBusinessType;
    @Column(name = "uWanalysisContractContractID")
    private String uWanalysisContractContractId;
    @Column(name = "uWanalysisContractEndorsementNumber")
    private Integer uWanalysisContractEndorsementNumber;
    @Column(name = "uWanalysisContractFacNumber")
    private String uWanalysisContractFacNumber;
    @Column(name = "uWanalysisContractInsured")
    private String uWanalysisContractInsured;
    @Column(name = "uWanalysisContractLabel")
    private String uWanalysisContractLabel;
    @Column(name = "uWanalysisContractLob")
    private String uWanalysisContractLob;
    @Column(name = "uWanalysisContractOrderNumber")
    private Integer uWanalysisContractOrderNumber;
    @Column(name = "uWanalysisContractSector")
    private String uWanalysisContractSector;
    @Column(name = "uWanalysisContractSubsidiary")
    private String uWanalysisContractSubsidiary;
    @Column(name = "uWanalysisContractYear")
    private Integer uWanalysisContractYear;
}
