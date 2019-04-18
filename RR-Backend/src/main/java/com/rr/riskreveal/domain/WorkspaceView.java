package com.rr.riskreveal.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "WORKSPACE_VIEW", schema = "BEREXIA\\amine.kharrou", catalog = "RR")
public class WorkspaceView {
    private String programid;
    private String programName;
    private String countryName;
    private String treatyid;
    private String treatyName;
    private Integer uwYear;
    private String workSpaceId;
    private String workspaceName;
    private Timestamp expiryDate;
    private Integer subsidiaryid;
    private String subsidiaryLedgerid;

    @Basic
    @Column(name = "Programid")
    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    @Basic
    @Column(name = "ProgramName")
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Basic
    @Column(name = "CountryName")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "Treatyid")
    public String getTreatyid() {
        return treatyid;
    }

    public void setTreatyid(String treatyid) {
        this.treatyid = treatyid;
    }

    @Basic
    @Column(name = "TreatyName")
    public String getTreatyName() {
        return treatyName;
    }

    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName;
    }

    @Basic
    @Column(name = "UwYear")
    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    @Basic
    @Column(name = "WorkSpaceId")
    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    @Basic
    @Column(name = "WorkspaceName")
    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    @Basic
    @Column(name = "ExpiryDate")
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Basic
    @Column(name = "Subsidiaryid")
    public Integer getSubsidiaryid() {
        return subsidiaryid;
    }

    public void setSubsidiaryid(Integer subsidiaryid) {
        this.subsidiaryid = subsidiaryid;
    }

    @Basic
    @Column(name = "SubsidiaryLedgerid")
    public String getSubsidiaryLedgerid() {
        return subsidiaryLedgerid;
    }

    public void setSubsidiaryLedgerid(String subsidiaryLedgerid) {
        this.subsidiaryLedgerid = subsidiaryLedgerid;
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
                Objects.equals(uwYear, that.uwYear) &&
                Objects.equals(workSpaceId, that.workSpaceId) &&
                Objects.equals(workspaceName, that.workspaceName) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(subsidiaryid, that.subsidiaryid) &&
                Objects.equals(subsidiaryLedgerid, that.subsidiaryLedgerid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(programid, programName, countryName, treatyid, treatyName, uwYear, workSpaceId, workspaceName, expiryDate, subsidiaryid, subsidiaryLedgerid);
    }
}
