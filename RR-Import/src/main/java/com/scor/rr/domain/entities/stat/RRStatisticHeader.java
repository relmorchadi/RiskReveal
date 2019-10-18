package com.scor.rr.domain.entities.stat;

import com.scor.rr.domain.entities.ihub.RRFinancialPerspective;
import com.scor.rr.domain.enums.StatisticMetric;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RRStatisticHeader database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "RRStatisticHeader")
@Data
public class RRStatisticHeader implements Cloneable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RRStatisticHeaderId")
    private String rrStatisticHeaderId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "LossDataType")
    private String lossDataType;
    @Column(name = "LossTableId")
    private String lossTableId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatisticDataId")
    private StatisticData statisticData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FinancialPerspectiveId")
    private RRFinancialPerspective financialPerspective;

    public RRStatisticHeader() {
    }

    public RRStatisticHeader(StatisticMetric statisticMetric, RRSummaryStatistic summaryStatistic,
                             List<RREPCurve> epCurves) {
        this.statisticData = new StatisticData(statisticMetric, summaryStatistic, epCurves);
    }
}
