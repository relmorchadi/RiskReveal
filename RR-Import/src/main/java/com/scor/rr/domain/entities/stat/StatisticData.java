package com.scor.rr.domain.entities.stat;

import com.scor.rr.domain.enums.StatisticMetric;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the StatisticData database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "StatisticData")
@Data
public class StatisticData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatisticDataId")
    private Long statisticDataId;
    @Column(name = "StatisticMetric")
    private StatisticMetric statisticMetric;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RRSummaryStatisticId")
    private RRSummaryStatistic summaryStatistic;
    @OneToMany(mappedBy = "statisticData")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RREPCurve> epCurves;

    public StatisticData() {
    }

    public StatisticData(StatisticMetric statisticMetric, RRSummaryStatistic summaryStatistic, List<RREPCurve> epCurves) {
        this.statisticMetric = statisticMetric;
        this.summaryStatistic = summaryStatistic;
        this.epCurves = epCurves;
    }
}
