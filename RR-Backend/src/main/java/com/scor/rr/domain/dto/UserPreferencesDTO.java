package com.scor.rr.domain.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserPreferencesDTO {

    private Long userPreferenceId;
    private String shortDate;
    private String longDate;
    private String shortTime;
    private String longTime;
    private String timeZone;
    private Integer numberOfDecimals;
    private Character decimalSeparator;
    private Character decimalThousandSeparator;
    private String negativeFormat;
    private Integer numberHistory;
    private String colors;
    // to be Enums
    private String importPage;
    private String financialPerspectiveELT;
    private String financialPerspectiveEPM;
    private String targetCurrency;
    private String targetAnalysisCurrency;
    private String defaultRmsInstance;
    private String countryCode;
    private Long uwUnitId;
    private Integer returnPeriod;
    private Boolean display;
}
