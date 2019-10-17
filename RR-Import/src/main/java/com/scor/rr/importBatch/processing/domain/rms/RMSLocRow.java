package com.scor.rr.importBatch.processing.domain.rms;

import java.util.Objects;

/**
 * Created by U002629 on 03/04/2015.
 */
public class RMSLocRow {
    private String carID;
    private String division;
    private String regionPerilCode;
    private String currencyCode;
    private Double expectedLoss;
    private String accuracyLevel;
    private String countryCode;
    private String regionCode;
    private String perilCode;
    private Integer locationID;
    private Integer locationNum;
    private String locationName;
    private String streetAddress;
    private String cityName;
    private String county;
    private String state;
    private String postalZipCode;
    private String bldgScheme;
    private String bldgClass;
    private String consName;
    private String occScheme;
    private Integer occType;
    private String occName;
    private String windZone;
    private String iso3A;
    private String countryRMSCode;
    private String admin1Code;
    private Double tivValue;
    private Double purePremiumGU;
    private Double purePremiumGR;
    private Double tivBuildings;
    private Double tivContents;
    private Double tivBI;
    private Double tivCombined;
    private Integer geoResultionCode;
    private String primaryFloodZone;
    private String neighboringFloodZones;
    private String annualProbabilityofFlooding;
    private Double distCoast;
    private Double elevation;
    private String soilTypeName;
    private String soilMatchLevel;
    private String liquefactionName;
    private String liquefactionMatchLevel;
    private String landslideName;
    private String landslideMatchLevel;

    public RMSLocRow(String carID, String division, String regionPerilCode, String currencyCode, Double expectedLoss, String accuracyLevel, String countryCode, String regionCode, String perilCode, Integer locationID, Integer locationNum, String locationName, String streetAddress, String cityName, String county, String state, String postalZipCode, String bldgScheme, String bldgClass, String consName, String occScheme, Integer occType, String occName, String windZone, String iso3A, String countryRMSCode, String admin1Code, Double tivValue, Double purePremiumGU, Double purePremiumGR, Double tivBuildings, Double tivContents, Double tivBI, Double tivCombined, Integer geoResultionCode, String primaryFloodZone, String neighboringFloodZones, String annualProbabilityofFlooding, Double distCoast, Double elevation, String soilTypeName, String soilMatchLevel, String liquefactionName, String liquefactionMatchLevel, String landslideName, String landslideMatchLevel) {
        this.carID = carID;
        this.division = division;
        this.regionPerilCode = regionPerilCode;
        this.currencyCode = currencyCode;
        this.expectedLoss = expectedLoss;
        this.accuracyLevel = accuracyLevel;
        this.countryCode = countryCode;
        this.regionCode = regionCode;
        this.perilCode = perilCode;
        this.locationID = locationID;
        this.locationNum = locationNum;
        this.locationName = locationName;
        this.streetAddress = streetAddress;
        this.cityName = cityName;
        this.county = county;
        this.state = state;
        this.postalZipCode = postalZipCode;
        this.bldgScheme = bldgScheme;
        this.bldgClass = bldgClass;
        this.consName = consName;
        this.occScheme = occScheme;
        this.occType = occType;
        this.occName = occName;
        this.windZone = windZone;
        this.iso3A = iso3A;
        this.countryRMSCode = countryRMSCode;
        this.admin1Code = admin1Code;
        this.tivValue = tivValue;
        this.purePremiumGU = purePremiumGU;
        this.purePremiumGR = purePremiumGR;
        this.tivBuildings = tivBuildings;
        this.tivContents = tivContents;
        this.tivBI = tivBI;
        this.tivCombined = tivCombined;
        this.geoResultionCode = geoResultionCode;
        this.primaryFloodZone = primaryFloodZone;
        this.neighboringFloodZones = neighboringFloodZones;
        this.annualProbabilityofFlooding = annualProbabilityofFlooding;
        this.distCoast = distCoast;
        this.elevation = elevation;
        this.soilTypeName = soilTypeName;
        this.soilMatchLevel = soilMatchLevel;
        this.liquefactionName = liquefactionName;
        this.liquefactionMatchLevel = liquefactionMatchLevel;
        this.landslideName = landslideName;
        this.landslideMatchLevel = landslideMatchLevel;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRegionPerilCode() {
        return regionPerilCode;
    }

    public void setRegionPerilCode(String regionPerilCode) {
        this.regionPerilCode = regionPerilCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getExpectedLoss() {
        return expectedLoss;
    }

    public void setExpectedLoss(Double expectedLoss) {
        this.expectedLoss = expectedLoss;
    }

    public String getAccuracyLevel() {
        return accuracyLevel;
    }

    public void setAccuracyLevel(String accuracyLevel) {
        this.accuracyLevel = accuracyLevel;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public Integer getLocationNum() {
        return locationNum;
    }

    public void setLocationNum(Integer locationNum) {
        this.locationNum = locationNum;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalZipCode() {
        return postalZipCode;
    }

    public void setPostalZipCode(String postalZipCode) {
        this.postalZipCode = postalZipCode;
    }

    public String getBldgScheme() {
        return bldgScheme;
    }

    public void setBldgScheme(String bldgScheme) {
        this.bldgScheme = bldgScheme;
    }

    public String getBldgClass() {
        return bldgClass;
    }

    public void setBldgClass(String bldgClass) {
        this.bldgClass = bldgClass;
    }

    public String getConsName() {
        return consName;
    }

    public void setConsName(String consName) {
        this.consName = consName;
    }

    public String getOccScheme() {
        return occScheme;
    }

    public void setOccScheme(String occScheme) {
        this.occScheme = occScheme;
    }

    public Integer getOccType() {
        return occType;
    }

    public void setOccType(Integer occType) {
        this.occType = occType;
    }

    public String getOccName() {
        return occName;
    }

    public void setOccName(String occName) {
        this.occName = occName;
    }

    public String getWindZone() {
        return windZone;
    }

    public void setWindZone(String windZone) {
        this.windZone = windZone;
    }

    public String getIso3A() {
        return iso3A;
    }

    public void setIso3A(String iso3A) {
        this.iso3A = iso3A;
    }

    public String getCountryRMSCode() {
        return countryRMSCode;
    }

    public void setCountryRMSCode(String countryRMSCode) {
        this.countryRMSCode = countryRMSCode;
    }

    public String getAdmin1Code() {
        return admin1Code;
    }

    public void setAdmin1Code(String admin1Code) {
        this.admin1Code = admin1Code;
    }

    public Double getTivValue() {
        return tivValue;
    }

    public void setTivValue(Double tivValue) {
        this.tivValue = tivValue;
    }

    public Double getPurePremiumGU() {
        return purePremiumGU;
    }

    public void setPurePremiumGU(Double purePremiumGU) {
        this.purePremiumGU = purePremiumGU;
    }

    public Double getPurePremiumGR() {
        return purePremiumGR;
    }

    public void setPurePremiumGR(Double purePremiumGR) {
        this.purePremiumGR = purePremiumGR;
    }

    public Double getTivBuildings() {
        return tivBuildings;
    }

    public void setTivBuildings(Double tivBuildings) {
        this.tivBuildings = tivBuildings;
    }

    public Double getTivContents() {
        return tivContents;
    }

    public void setTivContents(Double tivContents) {
        this.tivContents = tivContents;
    }

    public Double getTivBI() {
        return tivBI;
    }

    public void setTivBI(Double tivBI) {
        this.tivBI = tivBI;
    }

    public Double getTivCombined() {
        return tivCombined;
    }

    public void setTivCombined(Double tivCombined) {
        this.tivCombined = tivCombined;
    }

    public Integer getGeoResultionCode() {
        return geoResultionCode;
    }

    public void setGeoResultionCode(Integer geoResultionCode) {
        this.geoResultionCode = geoResultionCode;
    }

    public String getPrimaryFloodZone() {
        return primaryFloodZone;
    }

    public void setPrimaryFloodZone(String primaryFloodZone) {
        this.primaryFloodZone = primaryFloodZone;
    }

    public String getNeighboringFloodZones() {
        return neighboringFloodZones;
    }

    public void setNeighboringFloodZones(String neighboringFloodZones) {
        this.neighboringFloodZones = neighboringFloodZones;
    }

    public String getAnnualProbabilityofFlooding() {
        return annualProbabilityofFlooding;
    }

    public void setAnnualProbabilityofFlooding(String annualProbabilityofFlooding) {
        this.annualProbabilityofFlooding = annualProbabilityofFlooding;
    }

    public Double getDistCoast() {
        return distCoast;
    }

    public void setDistCoast(Double distCoast) {
        this.distCoast = distCoast;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public String getSoilTypeName() {
        return soilTypeName;
    }

    public void setSoilTypeName(String soilTypeName) {
        this.soilTypeName = soilTypeName;
    }

    public String getSoilMatchLevel() {
        return soilMatchLevel;
    }

    public void setSoilMatchLevel(String soilMatchLevel) {
        this.soilMatchLevel = soilMatchLevel;
    }

    public String getLiquefactionName() {
        return liquefactionName;
    }

    public void setLiquefactionName(String liquefactionName) {
        this.liquefactionName = liquefactionName;
    }

    public String getLiquefactionMatchLevel() {
        return liquefactionMatchLevel;
    }

    public void setLiquefactionMatchLevel(String liquefactionMatchLevel) {
        this.liquefactionMatchLevel = liquefactionMatchLevel;
    }

    public String getLandslideName() {
        return landslideName;
    }

    public void setLandslideName(String landslideName) {
        this.landslideName = landslideName;
    }

    public String getLandslideMatchLevel() {
        return landslideMatchLevel;
    }

    public void setLandslideMatchLevel(String landslideMatchLevel) {
        this.landslideMatchLevel = landslideMatchLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RMSLocRow rmsLocRow = (RMSLocRow) o;
        return Objects.equals(countryCode, rmsLocRow.countryCode) &&
                Objects.equals(regionCode, rmsLocRow.regionCode) &&
                Objects.equals(perilCode, rmsLocRow.perilCode) &&
                Objects.equals(locationID, rmsLocRow.locationID) &&
                Objects.equals(occType, rmsLocRow.occType) &&
                Objects.equals(occName, rmsLocRow.occName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, regionCode, perilCode, locationID, occType, occName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RMSLocRow{");
        sb.append("countryCode='").append(countryCode).append('\'');
        sb.append(", regionCode='").append(regionCode).append('\'');
        sb.append(", perilCode='").append(perilCode).append('\'');
        sb.append(", locationID=").append(locationID);
        sb.append(", locationNum=").append(locationNum);
        sb.append(", locationName='").append(locationName).append('\'');
        sb.append(", streetAddress='").append(streetAddress).append('\'');
        sb.append(", cityName='").append(cityName).append('\'');
        sb.append(", county='").append(county).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", postalZipCode='").append(postalZipCode).append('\'');
        sb.append(", bldgScheme='").append(bldgScheme).append('\'');
        sb.append(", bldgClass='").append(bldgClass).append('\'');
        sb.append(", consName='").append(consName).append('\'');
        sb.append(", occScheme='").append(occScheme).append('\'');
        sb.append(", occType=").append(occType);
        sb.append(", occName='").append(occName).append('\'');
        sb.append(", windZone='").append(windZone).append('\'');
        sb.append(", iso3A='").append(iso3A).append('\'');
        sb.append(", countryRMSCode='").append(countryRMSCode).append('\'');
        sb.append(", admin1Code='").append(admin1Code).append('\'');
        sb.append(", tivValue=").append(tivValue);
        sb.append(", purePremiumGU=").append(purePremiumGU);
        sb.append(", purePremiumGR=").append(purePremiumGR);
        sb.append(", tivBuildings=").append(tivBuildings);
        sb.append(", tivContents=").append(tivContents);
        sb.append(", tivBI=").append(tivBI);
        sb.append(", tivCombined=").append(tivCombined);
        sb.append(", geoResultionCode=").append(geoResultionCode);
        sb.append(", primaryFloodZone=").append(primaryFloodZone);
        sb.append(", neighboringFloodZones=").append(neighboringFloodZones);
        sb.append(", annualProbabilityofFlooding=").append(annualProbabilityofFlooding);
        sb.append(", distCoast=").append(distCoast);
        sb.append(", elevation=").append(elevation);
        sb.append(", soilTypeName=").append(soilTypeName);
        sb.append(", soilMatchLevel=").append(soilMatchLevel);
        sb.append(", liquefactionName=").append(liquefactionName);
        sb.append(", liquefactionMatchLevel=").append(liquefactionMatchLevel);
        sb.append(", landslideName=").append(landslideName);
        sb.append(", landslideMatchLevel=").append(landslideMatchLevel);
        sb.append('}');
        return sb.toString();
    }
}
