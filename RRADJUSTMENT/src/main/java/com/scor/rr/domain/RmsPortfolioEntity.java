package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RmsPortfolio", schema = "dbo", catalog = "RiskReveal")
public class RmsPortfolioEntity {
    private String agCedent;
    private String agCurrency;
    private String agSource;
    private Timestamp created;
    private String description;
    private Integer edmId;
    private String edmName;
    private String name;
    private String number;
    private String peril;
    private Integer portfolioId;
    private String type;

    @Id
    @Basic
    @Column(name = "agCedent", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAgCedent() {
        return agCedent;
    }

    public void setAgCedent(String agCedent) {
        this.agCedent = agCedent;
    }

    @Basic
    @Column(name = "agCurrency", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAgCurrency() {
        return agCurrency;
    }

    public void setAgCurrency(String agCurrency) {
        this.agCurrency = agCurrency;
    }

    @Basic
    @Column(name = "agSource", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAgSource() {
        return agSource;
    }

    public void setAgSource(String agSource) {
        this.agSource = agSource;
    }

    @Basic
    @Column(name = "created", nullable = true)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "edmId", nullable = true)
    public Integer getEdmId() {
        return edmId;
    }

    public void setEdmId(Integer edmId) {
        this.edmId = edmId;
    }

    @Basic
    @Column(name = "edmName", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getEdmName() {
        return edmName;
    }

    public void setEdmName(String edmName) {
        this.edmName = edmName;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "number", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "peril", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    @Basic
    @Column(name = "portfolioId", nullable = true)
    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RmsPortfolioEntity that = (RmsPortfolioEntity) o;
        return Objects.equals(agCedent, that.agCedent) &&
                Objects.equals(agCurrency, that.agCurrency) &&
                Objects.equals(agSource, that.agSource) &&
                Objects.equals(created, that.created) &&
                Objects.equals(description, that.description) &&
                Objects.equals(edmId, that.edmId) &&
                Objects.equals(edmName, that.edmName) &&
                Objects.equals(name, that.name) &&
                Objects.equals(number, that.number) &&
                Objects.equals(peril, that.peril) &&
                Objects.equals(portfolioId, that.portfolioId) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agCedent, agCurrency, agSource, created, description, edmId, edmName, name, number, peril, portfolioId, type);
    }
}
