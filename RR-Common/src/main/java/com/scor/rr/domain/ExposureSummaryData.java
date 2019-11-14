package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureSummaryData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GlobalExposureViewId")
    private Long exposureSummaryDataId;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "PerilCode")
    private String perilCode;
    @Column(name = "Admin1Code")
    private String admin1Code;
    @Column(name = "RegionPerilCode")
    private String regionPerilCode;
    @Column(name = "Metric")
    private String metric;
    @Column(name = "TIV")
    private Double tiv;
    @Column(name = "AvgTIV")
    private Double avgTiv;
    @Column(name = "LocationCount")
    private Double locationCount;

    @ManyToOne
    @JoinColumn(name = "GlobalViewSummaryId")
    private GlobalViewSummary globalViewSummary;
}
