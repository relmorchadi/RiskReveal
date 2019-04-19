package com.rr.riskreveal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(WorkspaceViewId.class)
@Table(name = "WORKSPACE_VIEW", schema = "dbo")
public class WorkspaceView implements Serializable{
    @Id
    @Column(name = "UwYear")
    private Integer uwYear;
    @Id
    @Column(name = "WorkSpaceId")
    private String workSpaceId;
    @Column(name = "WorkspaceName")
    private String workspaceName;
    @Column(name = "Programid")
    private String programid;
    @Column(name = "ProgramName")
    private String programName;
    @Column(name = "CountryName")
    private String countryName;
    @Column(name = "Treatyid")
    private String treatyid;
    @Column(name = "TreatyName")
    private String treatyName;
    @Column(name = "ExpiryDate")
    private Timestamp expiryDate;
    @Column(name = "Subsidiaryid")
    private Integer subsidiaryid;
    @Column(name = "SubsidiaryLedgerid")
    private String subsidiaryLedgerid;
    @Column(name = "cedantCode")
    private String cedantCode;
    @Column(name = "cedantName")
    private String cedantName;

    public WorkspaceView() {
    }

    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public String getTreatyid() {
        return treatyid;
    }

    public void setTreatyid(String treatyid) {
        this.treatyid = treatyid;
    }

    public String getTreatyName() {
        return treatyName;
    }

    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }


    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }


    public Integer getSubsidiaryid() {
        return subsidiaryid;
    }

    public void setSubsidiaryid(Integer subsidiaryid) {
        this.subsidiaryid = subsidiaryid;
    }


    public String getSubsidiaryLedgerid() {
        return subsidiaryLedgerid;
    }

    public void setSubsidiaryLedgerid(String subsidiaryLedgerid) {
        this.subsidiaryLedgerid = subsidiaryLedgerid;
    }

    public String getCedantCode() {
        return cedantCode;
    }

    public void setCedantCode(String cedantCode) {
        this.cedantCode = cedantCode;
    }

    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceView that = (WorkspaceView) o;
        return Objects.equals(programid, that.programid) &&
                Objects.equals(programName, that.programName) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(treatyName, that.treatyName) &&
                Objects.equals(workspaceName, that.workspaceName) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(subsidiaryid, that.subsidiaryid) &&
                Objects.equals(subsidiaryLedgerid, that.subsidiaryLedgerid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programid, programName, countryName, treatyid, treatyName, workspaceName, expiryDate, subsidiaryid, subsidiaryLedgerid);
    }


}

