package com.scor.rr.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringPackageProcessing", schema = "dbo", catalog = "RiskReveal")
public class InuringPackageProcessing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringContractParamId", nullable = false)
    private long inuringPackageProcessingId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringPackageId", nullable = false)
    private long inuringPackageId;

    @Column(name = "SubmittedDate")
    private Date submittedDate;

    @Column(name = "SubmittedBy")
    private int submittedBy;

    @Column(name = "StartedDate")
    private Date startedDate;

    @Column(name = "EndedDate")
    private Date endedDate;

    @Column(name = "InuringProcessingStatus")
    private int inuringProcessingStatus;

    @Column(name = "InputFileName")
    private String inputFileName;

    @Column(name = "InputFilePath")
    private String inputFilePath;

    public InuringPackageProcessing() {
    }
}
