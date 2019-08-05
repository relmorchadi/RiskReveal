package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RR-PltHeader-old", schema = "dbo", catalog = "RiskReveal")
public class RrPltHeaderOldEntity {
    private String id;
    private String currencyCode;
    private String geoCode;
    private String perilCode;
    private String fileName;
    private String pltType;
    private String project;
    private String publishToArc;
    private String regionPeril;
    private String rrAnalysisId;
    private String targetRap;
    private String udName;
    private String userSelectedGrain;
    private String xActPublicationDate;

    @Id
    @Column(name = "_id", nullable = false, length = 255,insertable = false ,updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "currencyCode", length = 255,insertable = false ,updatable = false)
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Basic
    @Column(name = "geoCode", length = 255,insertable = false ,updatable = false)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "perilCode", length = 255,insertable = false ,updatable = false)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "fileName", length = 255,insertable = false ,updatable = false)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "pltType", length = 255,insertable = false ,updatable = false)
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "project", length = 255,insertable = false ,updatable = false)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Basic
    @Column(name = "publishToArc", length = 255,insertable = false ,updatable = false)
    public String getPublishToArc() {
        return publishToArc;
    }

    public void setPublishToArc(String publishToArc) {
        this.publishToArc = publishToArc;
    }

    @Basic
    @Column(name = "regionPeril", length = 255,insertable = false ,updatable = false)
    public String getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(String regionPeril) {
        this.regionPeril = regionPeril;
    }

    @Basic
    @Column(name = "rrAnalysisId", length = 255,insertable = false ,updatable = false)
    public String getRrAnalysisId() {
        return rrAnalysisId;
    }

    public void setRrAnalysisId(String rrAnalysisId) {
        this.rrAnalysisId = rrAnalysisId;
    }

    @Basic
    @Column(name = "targetRap", length = 255,insertable = false ,updatable = false)
    public String getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(String targetRap) {
        this.targetRap = targetRap;
    }

    @Basic
    @Column(name = "udName", length = 255,insertable = false ,updatable = false)
    public String getUdName() {
        return udName;
    }

    public void setUdName(String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "userSelectedGrain", length = 255,insertable = false ,updatable = false)
    public String getUserSelectedGrain() {
        return userSelectedGrain;
    }

    public void setUserSelectedGrain(String userSelectedGrain) {
        this.userSelectedGrain = userSelectedGrain;
    }

    @Basic
    @Column(name = "xActPublicationDate", length = 255,insertable = false ,updatable = false)
    public String getxActPublicationDate() {
        return xActPublicationDate;
    }

    public void setxActPublicationDate(String xActPublicationDate) {
        this.xActPublicationDate = xActPublicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrPltHeaderOldEntity that = (RrPltHeaderOldEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(geoCode, that.geoCode) &&
                Objects.equals(perilCode, that.perilCode) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(pltType, that.pltType) &&
                Objects.equals(project, that.project) &&
                Objects.equals(publishToArc, that.publishToArc) &&
                Objects.equals(regionPeril, that.regionPeril) &&
                Objects.equals(rrAnalysisId, that.rrAnalysisId) &&
                Objects.equals(targetRap, that.targetRap) &&
                Objects.equals(udName, that.udName) &&
                Objects.equals(userSelectedGrain, that.userSelectedGrain) &&
                Objects.equals(xActPublicationDate, that.xActPublicationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, currencyCode, geoCode, perilCode, fileName, pltType, project, publishToArc, regionPeril, rrAnalysisId, targetRap, udName, userSelectedGrain, xActPublicationDate);
    }
}
