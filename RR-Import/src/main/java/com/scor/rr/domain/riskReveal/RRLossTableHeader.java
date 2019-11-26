package com.scor.rr.domain.riskReveal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RRLossTableHeader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RRLossTableHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RRLossTableHeaderId")
    private Long rrLossTableHeaderId;
    @Column(name = "RREntity")
    private Integer entity;
    @Column(name = "LossTableType")
    private String lossTableType;
    @Column(name = "OriginalTarget")
    private String originalTarget;
    @Column(name = "Currency")
    private String currency;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "LossDataFilePath")
    private String lossDataFilePath;
    @Column(name = "LossDataFileName")
    private String lossDataFileName;
    @Column(name = "FileDataFormat")
    private String fileDataFormat;
    @Column(name = "FileType")
    private String fileType;

    @OneToOne
    @JoinColumn(name = "RRAnalysisId")
    private RRAnalysis rrAnalysis;
}
