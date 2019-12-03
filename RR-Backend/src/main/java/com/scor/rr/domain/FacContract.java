//package com.scor.rr.domain;
//
//import com.scor.rr.util.StringPrefixedSequenceIdGenerator;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.id.enhanced.SequenceStyleGenerator;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "FacContracts")
//public class FacContract {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
//    @GenericGenerator(
//            name = "car_seq",
//            strategy = "com.scor.rr.util.StringPrefixedSequenceIdGenerator",
//            parameters = {
//                    @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM,value = "1"),
//                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
//                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CAR-"),
//                    @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
//    @Column(name = "id")
//    private String id;
//    @Column(name = "lastUpdateDate")
//    private String lastUpdateDate;
//    @Column(name = "lastUpdatedBy")
//    private String lastUpdatedBy;
//    @Column(name = "requestedByFirstName")
//    private String requestedByFirstName;
//    @Column(name = "requestedByLastName")
//    private String requestedByLastName;
//    @Column(name = "requestedByFullName")
//    private String requestedByFullName;
//    @Column(name = "requestCreationDate")
//    private String requestCreationDate;
//    @Column(name = "uWanalysisContractBusinessType")
//    private String uWanalysisContractBusinessType;
//    @Column(name = "uWanalysisContractContractID")
//    private String uWanalysisContractContractId;
//    @Column(name = "uWanalysisContractEndorsementNumber")
//    private Integer uWanalysisContractEndorsementNumber;
//    @Column(name = "uWanalysisContractFacNumber")
//    private String uWanalysisContractFacNumber;
//    @Column(name = "uWanalysisContractInsured")
//    private String uWanalysisContractInsured;
//    @Column(name = "uWanalysisContractLabel")
//    private String uWanalysisContractLabel;
//    @Column(name = "uWanalysisContractLob")
//    private String uWanalysisContractLob;
//    @Column(name = "uWanalysisContractOrderNumber")
//    private Integer uWanalysisContractOrderNumber;
//    @Column(name = "uWanalysisContractSector")
//    private String uWanalysisContractSector;
//    @Column(name = "uWanalysisContractSubsidiary")
//    private String uWanalysisContractSubsidiary;
//    @Column(name = "uWanalysisContractYear")
//    private Integer uWanalysisContractYear;
//}
