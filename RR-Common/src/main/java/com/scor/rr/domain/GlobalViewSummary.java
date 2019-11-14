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
public class GlobalViewSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GlobalViewSummaryId")
    private Long globalViewSummaryId;
    @Column(name = "SummaryTitle")
    private Long summaryTitle;
    @Column(name = "SummaryOrder")
    private Long summaryOrder;

    @ManyToOne
    @JoinColumn(name = "GlobalExposureViewId")
    private GlobalExposureView globalExposureView;

    @OneToMany(mappedBy = "globalViewSummary", fetch = FetchType.LAZY)
    private List<ExposureSummaryData> exposureSummaryDataList;
}
