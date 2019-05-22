package com.scor.rr.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class PortfolioView {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "dataSourceId")
    private Integer dataSourceId;
    @Column(name = "dataSourceName")
    private String dataSourceName;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Column(name = "descriptionType")
    private String descriptionType;
    @Column(name = "edmId")
    private Integer edmId;
    @Column(name = "edmName")
    private String edmName;

    public PortfolioView() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }


    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }


    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }


    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }

    public Integer getEdmId() {
        return edmId;
    }

    public void setEdmId(Integer edmId) {
        this.edmId = edmId;
    }

    public String getEdmName() {
        return edmName;
    }

    public void setEdmName(String edmName) {
        this.edmName = edmName;
    }
}
