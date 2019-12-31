package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vw_Calibration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalibrationView {

    private String workspaceContextCode;
    private Integer uwYear;

    @Id
    private Long pltId;

    private String pltName;

    private String pltType;

    @Column(length = 25)
    private String status;

    private Long regionPerilId;

    private String peril;

    private String regionPerilCode;

    private String regionPerilDesc;

    private String grain;

    private String vendorSystem;

    private Long targetRapId;

    private String rap;

    private Long projectId;

    private String projectName;

    private String pltCcy;

    private Double stdDev;

    private Double cov;

    private Double oep10;

    private Double oep50;

    private Double oep100;

    private Double oep250;

    private Double oep500;

    private Double oep1000;

    private Double aep10;

    private Double aep50;

    private Double aep100;

    private Double aep250;

    private Double aep500;

    private Double aep1000;

    private Long pureId;

    private Long threadId;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "pureId")
    private List<CalibrationView> threads;
}
