package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "RefFMFContractAttribute")
public class RefFMFContractAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContractAttributeID", nullable = false)
    private long contractAttributeId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "UIAttributeName")
    private String uIAttributeName;

    @Column(name = "AttributeDescription")
    private String attributeDescription;

    @Column(name = "TargetRelease")
    private int targetRelease;

    @Column(name = "UISectionName")
    private String uISectionName;

    @Column(name = "UISection")
    private int uISection;

    @Column(name = "UISectionSequence")
    private int uISectionSequence;

    @Column(name = "UIVisible")
    private String uIVisible;

    @Column(name = "AttributeLabel")
    private String attributeLabel;

    @Column(name = "UserInput")
    private String userInput;

    @Column(name = "AttributeCode")
    private String attributeCode;

    @Column(name = "DataType")
    private String dataType;

    @Column(name = "UIElement")
    private String uIElement;

    @Column(name = "UIAlignment")
    private String uIAlignment;

    @Column(name = "DefaultValue")
    private String defaultValue;

    @Column(name = "ValueValidation")
    private String valueValidation;

    @Column(name = "DefaultValueNotes")
    private String defaultValueNotes;

    public RefFMFContractAttribute() {
    }

    public RefFMFContractAttribute(String uIAttributeName, String dataType, String defaultValue) {
        this.uIAttributeName = uIAttributeName;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
    }
}
