package com.scor.rr.domain.dto;


import org.springframework.util.StringUtils;

public class PltFilter {

    private String pltId;
    private String pltName;
    private String peril;
    private String regionPerilCode;
    private String regionPerilName;
    private String grain;
    private String vendorSystem;
    private String rap;
    private String workspaceId;
    private Integer uwy;
    private String project;

    public PltFilter() {
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPltId() {
        return pltId;
    }

    public void setPltId(String pltId) {
        this.pltId = StringUtils.isEmpty(pltId) ? null: pltId;
    }

    public String getPltName() {
        return pltName;
    }

    public void setPltName(String pltName) {
        this.pltName = StringUtils.isEmpty(pltName) ? null: pltName;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = StringUtils.isEmpty(peril) ? null: peril;
    }

    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = StringUtils.isEmpty(regionPerilCode) ? null: regionPerilCode;
    }

    public String getRegionPerilName() {
        return regionPerilName;
    }

    public void setRegionPerilName(String regionPerilName) {
        this.regionPerilName = StringUtils.isEmpty(regionPerilName) ? null: regionPerilName;
    }

    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = StringUtils.isEmpty(grain) ? null: grain;
    }

    public String getVendorSystem() {
        return vendorSystem;
    }

    public void setVendorSystem(String vendorSystem) {
        this.vendorSystem = StringUtils.isEmpty(vendorSystem) ? null: vendorSystem;
    }

    public String getRap() {
        return rap;
    }

    public void setRap(String rap) {
        this.rap = StringUtils.isEmpty(rap) ? null: rap;
    }

}
