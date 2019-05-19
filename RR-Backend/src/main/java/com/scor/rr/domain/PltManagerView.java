package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PltManagerView {

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

    public PltManagerView() {
    }

    public String getPltId() {
        return pltId;
    }

    public void setPltId(String id) {
        this.pltId = id;
    }


    public String getPltName() {
        return pltName;
    }

    public void setPltName(String name) {
        this.pltName = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PltManagerView that = (PltManagerView) o;
        return Objects.equals(pltId, that.pltId) &&
                Objects.equals(pltName, that.pltName) &&
                Objects.equals(peril, that.peril) &&
                Objects.equals(regionPerilCode, that.regionPerilCode) &&
                Objects.equals(regionPerilName, that.regionPerilName) &&
                Objects.equals(grain, that.grain) &&
                Objects.equals(vendorSystem, that.vendorSystem) &&
                Objects.equals(rap, that.rap) &&
                Objects.equals(isScorCurrent, that.isScorCurrent) &&
                Objects.equals(isScorDefault, that.isScorDefault) &&
                Objects.equals(isScorGenerated, that.isScorGenerated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pltId, pltName, peril, regionPerilCode, regionPerilName, grain, vendorSystem, rap, isScorCurrent, isScorDefault, isScorGenerated);
    }
}
