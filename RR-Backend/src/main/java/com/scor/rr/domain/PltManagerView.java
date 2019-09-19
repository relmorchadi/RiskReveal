package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(schema = "poc")
@Data
@AllArgsConstructor
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
    @Column(name = "targetRapCode")
    private String targetRapCode;
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
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "sourceModellingVendor")
    private String  sourceModellingVendor;
    @Column(name = "sourceModellingSystem")
    private String  sourceModellingSystem;
    @Column(name = "dataSourceName")
    private String  dataSourceName;
    @Column(name = "analysisId")
    private String  analysisId;
    @Column(name = "xActPublicationDate")
    private String xActPublicationDate;
    @Column(name = "currency")
    private String currency;
    @Column(name = "userOccurrenceBasis")
    private String userOccurrenceBasis;

//    @OneToMany(mappedBy = "userTagPltPk.tag")
//    Set<UserTagPlt> userTags;

    @ManyToMany
    @JoinTable(
            name = "user_tag_plt",
            joinColumns = @JoinColumn(name = "fk_plt"),
            inverseJoinColumns = @JoinColumn(name = "fk_tag"))
    Set<UserTag> userTags;

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

    public String getTargetRapCode() {
        return targetRapCode;
    }

    public void setTargetRapCode(String targetRapCode) {
        this.targetRapCode = targetRapCode;
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
