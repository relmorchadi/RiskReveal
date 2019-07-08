package com.scor.rr.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PortfolioFilter {
    private String id;
    private Integer dataSourceId;
    private String dataSourceName;
    private Timestamp creationDate;
    private String descriptionType;
    private Integer edmId;
    private String edmName;
    private String agCedent;
    private String agCurrency;
    private String agSource;
    private String number;
    private String peril;
    private String portfolioId;
    private String type;

    public PortfolioFilter() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = StringUtils.isEmpty(id) ? null: id;
    }

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = StringUtils.isEmpty(dataSourceId) ? null: dataSourceId;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = StringUtils.isEmpty(dataSourceName) ? null: dataSourceName;
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
        this.descriptionType = StringUtils.isEmpty(descriptionType) ? null: descriptionType;
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
