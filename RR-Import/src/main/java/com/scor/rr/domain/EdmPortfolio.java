package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EdmPortfolio {

    private Long edmId;
    private String edmName;
    private Long portfolioId;
    private String number;
    private String name;
    private String created;
    private String description;
    private String type;
    private String peril;
    private String agSource;
    private String agCedant;
    private String agCurrency;
    private BigDecimal tiv;

    public Long getEdmId() {
        return edmId;
    }

    public void setEdmId(Long edmId) {
        this.edmId = edmId;
    }

    public String getEdmName() {
        return edmName;
    }

    public void setEdmName(String edmName) {
        this.edmName = edmName;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public String getAgSource() {
        return agSource;
    }

    public void setAgSource(String agSource) {
        this.agSource = agSource;
    }

    public String getAgCedant() {
        return agCedant;
    }

    public void setAgCedant(String agCedant) {
        this.agCedant = agCedant;
    }

    public String getAgCurrency() {
        return agCurrency;
    }

    public void setAgCurrency(String agCurrency) {
        this.agCurrency = agCurrency;
    }

    public BigDecimal getTiv() {
        return tiv;
    }

    public void setTiv(BigDecimal tiv) {
        this.tiv = tiv;
    }
}
