package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Table(name = "GlobalExposureViewSummary")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalExposureViewSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GlobalExposureViewSummaryId")
    private Long globalViewSummaryId;
    @Column(name="Entity")
    private Integer entity = 1;
    @Column(name = "SummaryTitle")
    private String summaryTitle;
    @Column(name = "SummaryOrder")
    private Integer summaryOrder;
    @Column(name = "InstanceId")
    private String instanceId;
    @Column(name = "EdmId")
    private Long edmId;
    @Column(name = "EdmName")
    private String edmName;

    @ManyToOne
    @JoinColumn(name = "GlobalExposureViewId")
    private GlobalExposureView globalExposureView;

    @OneToMany(mappedBy = "globalViewSummary", fetch = FetchType.LAZY)
    private List<ExposureSummaryData> exposureSummaryDataList;
}
