package com.scor.rr.domain.importfile;

import javax.persistence.*;

@Entity
@Table(name = "PEQTFileSchema", schema = "dbo")
public class PEQTFileSchema {
    private String id;
    private String peqtFileTypeID;
    private String field;
    private int order;
    private String type;
    private String mappedPeqtField;

    public PEQTFileSchema() {
    }

    @Id
    @Column(name = "PEQTFileSchemaId", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PEQTFileTypeID", nullable = true, length = 200)
    public String getPeqtFileTypeID() {
        return peqtFileTypeID;
    }

    public void setPeqtFileTypeID(String peqtFileTypeID) {
        this.peqtFileTypeID = peqtFileTypeID;
    }

    @Basic
    @Column(name = "Field", nullable = true, length = 200)
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Basic
    @Column(name = "OrderSchema", nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Basic
    @Column(name = "Type", nullable = true, length = 200)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "MappedPeqtField", nullable = true, length = 1000)
    public String getMappedPeqtField() {
        return mappedPeqtField;
    }

    public void setMappedPeqtField(String mappedPeqtField) {
        this.mappedPeqtField = mappedPeqtField;
    }

    public PEQTFileSchema(String peqtFileTypeID, String field, int order, String type, String mappedPeqtField) {
        this.peqtFileTypeID = peqtFileTypeID;
        this.field = field;
        this.order = order;
        this.type = type;
        this.mappedPeqtField = mappedPeqtField;
        this.setId(peqtFileTypeID + "_" + field);
    }
}

