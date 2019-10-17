package com.scor.rr.domain.entities.references.cat;

import com.scor.rr.domain.entities.cat.CATRequest;
import com.scor.rr.domain.entities.references.omega.PeriodBasis;
import com.scor.rr.domain.enums.ProcessStatuses;

import javax.persistence.*;

/**
 * The persistent class for the ProcessStatus database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ProcessStatus")
public class ProcessStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProcessStatusId")
    private Long processStatusId;
    @Column(name = "DivisionNumber")
    private Integer divisionNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CatRequestId")
    private CATRequest catRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PeriodBasisId")
    private PeriodBasis periodBasis;
    @Column(name = "ProcessStatus")
    private ProcessStatuses processStatus;
    @Column(name = "RunningStatus")
    private ProcessStatuses runningStatus;

    public ProcessStatus(Integer divisionNumber, CATRequest catRequest, PeriodBasis periodBasis,
                         ProcessStatuses processStatus, ProcessStatuses runningStatus) {
        this.divisionNumber = divisionNumber;
        this.catRequest = catRequest;
        this.periodBasis = periodBasis;
        this.processStatus = processStatus;
        this.runningStatus = runningStatus;
    }
}
