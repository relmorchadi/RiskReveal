package com.scor.rr.domain.entities.userPreferences;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "vw_UserPreference")
@Data
@NoArgsConstructor
public class UserPreferenceView {


    private Long userPreferenceId;

    @Id
    private Long userId;
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
