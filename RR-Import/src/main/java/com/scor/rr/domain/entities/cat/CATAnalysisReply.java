package com.scor.rr.domain.entities.cat;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the CATAnalysisReply database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CATAnalysisReply")
public class CATAnalysisReply {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "CATAnalysisReplyId")
    private String catAnalysisReplyId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATAnalysisAggregatedId")
    private CATAnalysis catAnalysisAggregated;
}
