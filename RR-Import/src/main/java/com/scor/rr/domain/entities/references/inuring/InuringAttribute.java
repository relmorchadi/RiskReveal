package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the InuringAttribute database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "InuringAttribute")
@Data
public class InuringAttribute {
    @Id
    @Column(name = "AttributeId")
    private Long attributeId;
    @Column(name = "AttributeName")
    private String attributeName;
    @Column(name = "AttributeDescription")
    private String attributeDesc;
    @Column(name = "UiOrganisation")
    private String uiOrganisation;
    @Column(name = "UiSection")
    private Integer uiSection;
    @Column(name = "UiSectionSequence")
    private Integer uiSectionSequence;
    @Column(name = "Visible")
    private Boolean visible;
    @Column(name = "UiLabel")
    private String uiLabel;
    @Column(name = "UserInput")
    private Boolean userInput;
    @Column(name = "AttributeCode")
    private String attributeCode;
    @Column(name = "DataType")
    private String dataType;
    @Column(name = "UiElement")
    private String uiElement;
    @Column(name = "UiAlignment")
    private String uiAlignment;
    @Column(name = "DefaultValue")
    private String defaultValue;
    @Column(name = "ValueValidation")
    private String valueValidation;
    @Column(name = "Notes")
    private String notes;
}
