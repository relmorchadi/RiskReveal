package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RR-PltHeader", schema = "dbo", catalog = "RiskReveal")
public class RrPltHeaderEntity {
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
    @Basic
    @Column(name = "_id", nullable = true, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "currencyCode", nullable = true, length = 255)
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Basic
    @Column(name = "geoCode", nullable = true, length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "perilCode", nullable = true, length = 255)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "fileName", nullable = true, length = 255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "pltType", nullable = true, length = 255)
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "project", nullable = true, length = 255)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Basic
    @Column(name = "publishToArc", nullable = true, length = 255)
    public String getPublishToArc() {
        return publishToArc;
    }

    public void setPublishToArc(String publishToArc) {
        this.publishToArc = publishToArc;
    }

    @Basic
    @Column(name = "regionPeril", nullable = true, length = 255)
    public String getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(String regionPeril) {
        this.regionPeril = regionPeril;
    }

    @Basic
    @Column(name = "rrAnalysisId", nullable = true, length = 255)
    public String getRrAnalysisId() {
        return rrAnalysisId;
    }

    public void setRrAnalysisId(String rrAnalysisId) {
        this.rrAnalysisId = rrAnalysisId;
    }

    @Basic
    @Column(name = "targetRap", nullable = true, length = 255)
    public String getTargetRap() {
        return targetRap;
    }

    public void setTargetRap(String targetRap) {
        this.targetRap = targetRap;
    }

    @Basic
    @Column(name = "udName", nullable = true, length = 255)
    public String getUdName() {
        return udName;
    }

    public void setUdName(String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "userSelectedGrain", nullable = true, length = 255)
    public String getUserSelectedGrain() {
        return userSelectedGrain;
    }

    public void setUserSelectedGrain(String userSelectedGrain) {
        this.userSelectedGrain = userSelectedGrain;
    }

    @Basic
    @Column(name = "xActPublicationDate", nullable = true, length = 255)
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
        RrPltHeaderEntity that = (RrPltHeaderEntity) o;
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
