package com.scor.rr.domain.importFile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MetadataHeaderSection", schema = "dbo", catalog = "RiskReveal")
public class MetadataHeaderSectionEntity {
    private int id;
    private String metadataAttribute;
    private String description;
    private String dataType;
    private String mandatory;
    private String format;
    private String assertValue;
    private String defaultValue;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MetadataAttribute", length = 100)
    public String getMetadataAttribute() {
        return metadataAttribute;
    }

    public void setMetadataAttribute(String metadataAttribute) {
        this.metadataAttribute = metadataAttribute;
    }

    @Basic
    @Column(name = "Description", length = 5000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "DataType", length = 100)
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Basic
    @Column(name = "Mandatory")
    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    @Basic
    @Column(name = "format")
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Basic
    @Column(name = "assertValue")
    public String getAssertValue() {
        return assertValue;
    }

    public void setAssertValue(String assertValue) {
        this.assertValue = assertValue;
    }

    @Basic
    @Column(name = "defaultValue")
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataHeaderSectionEntity that = (MetadataHeaderSectionEntity) o;
        return id == that.id &&
                Objects.equals(metadataAttribute, that.metadataAttribute) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dataType, that.dataType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metadataAttribute, description, dataType);
    }
}
