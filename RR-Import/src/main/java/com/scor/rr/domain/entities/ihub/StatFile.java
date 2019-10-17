package com.scor.rr.domain.entities.ihub;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the StatFile database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "StatFile")
@Data
public class StatFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "EPCFileName")
    private String epcFileName;
    @Column(name = "EPCFilePath")
    private String epcFilePath;
    @Column(name = "EPSFileName")
    private String epsFileName;
    @Column(name = "EPSFilePath")
    private String epsFilePath;
//    @Column(name = "StatisticHeaders")
//    private String statisticHeaders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RRLossTableId")
    private RRLossTable rrLossTable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FinancialPerspectiveId")
    private RRFinancialPerspective financialPerspective;


    @Transient
    private List<String> statisticHeaders;
}
