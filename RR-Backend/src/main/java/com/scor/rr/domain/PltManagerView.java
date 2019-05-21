package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PltManagerView {

    @Column(name = "workspaceId")
    private String workspaceId;
    @Column(name = "uwy")
    private Integer uwy;
    @Id
    @Column(name = "id")
    private String pltId;
    @Column(name = "name")
    private String pltName;
    @Column(name = "peril")
    private String peril;
    @Column(name = "regionPerilCode")
    private String regionPerilCode;
    @Column(name = "regionPerilName")
    private String regionPerilName;
    @Column(name = "grain")
    private String grain;
    @Column(name = "vendorSystem")
    private String vendorSystem;
    @Column(name = "rap")
    private String rap;
    @Column(name = "isScorCurrent")
    private String isScorCurrent;
    @Column(name = "isScorDefault")
    private String isScorDefault;
    @Column(name = "isScorGenerated")
    private String isScorGenerated;

    @Column(name = "project")
    private String project;
    @Column(name = "pltType")
    private String pltType;

    @Column(name = "projectName")
    private String projectName;
    @Column(name = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date creationDate;
    @Column(name = "year")
    private Integer year;

    public PltManagerView() {
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public Integer getUwy() {
        return uwy;
    }

    public void setUwy(Integer uwy) {
        this.uwy = uwy;
    }

    public String getPltId() {
        return pltId;
    }

    public void setPltId(String pltId) {
        this.pltId = pltId;
    }

    public String getPltName() {
        return pltName;
    }

    public void setPltName(String pltName) {
        this.pltName = pltName;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = regionPerilCode;
    }

    public String getRegionPerilName() {
        return regionPerilName;
    }

    public void setRegionPerilName(String regionPerilName) {
        this.regionPerilName = regionPerilName;
    }

    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = grain;
    }

    public String getVendorSystem() {
        return vendorSystem;
    }

    public void setVendorSystem(String vendorSystem) {
        this.vendorSystem = vendorSystem;
    }

    public String getRap() {
        return rap;
    }

    public void setRap(String rap) {
        this.rap = rap;
    }

    public String getIsScorCurrent() {
        return isScorCurrent;
    }

    public void setIsScorCurrent(String isScorCurrent) {
        this.isScorCurrent = isScorCurrent;
    }

    public String getIsScorDefault() {
        return isScorDefault;
    }

    public void setIsScorDefault(String isScorDefault) {
        this.isScorDefault = isScorDefault;
    }

    public String getIsScorGenerated() {
        return isScorGenerated;
    }

    public void setIsScorGenerated(String isScorGenerated) {
        this.isScorGenerated = isScorGenerated;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
