package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalExposureView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GlobalExposureViewId")
    private Long globalExposureViewId;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "DivisionNumber")
    private Integer divisionNumber;
    @Column(name = "PeriodBasisId")
    private Integer PeriodBasisId;
    @Column(name = "Version")
    private Integer version;
    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "globalExposureView", fetch = FetchType.LAZY)
    private List<GlobalViewSummary> globalViewSummaries;
}
