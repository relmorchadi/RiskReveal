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
    @Column(name="Entity")
    private Integer entity = 1;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "PeriodBasisId")
    private Integer PeriodBasisId;
    @Column(name = "Version")
    private Integer version;
    @Column(name = "GlobalExposureViewName")
    private String name;

    @OneToMany(mappedBy = "globalExposureView", fetch = FetchType.LAZY)
    private List<GlobalExposureViewSummary> globalViewSummaries;
}
