package com.scor.rr.service.batch.processor.rows;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLLocRow {

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
}
