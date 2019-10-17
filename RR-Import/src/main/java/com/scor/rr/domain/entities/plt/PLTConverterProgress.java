package com.scor.rr.domain.entities.plt;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Hamiani Mohammed
 * creation date  17/09/2019 at 11:33
 **/
@Entity
@Table(name = "TTPLTConverterProgress")
@Data
public class PLTConverterProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pltId;
    private String projectId;
    private long rdmId;
    private String rdmName;
    private String analysisId;
    private String peqtFilename;
    private long importId;
    private Date startConvert;
    private Date endConvert;
    private Date startCalcStat;
    private Date endCalcStat;
    private Date startDefaultAdj;
    private Date endDefaultAdj;

    public PLTConverterProgress(String scorPLTHeaderId, String projectId, Long rdmId, String rdmName, String analysisId, String peqtFilename, Long importId) {
        this.pltId = scorPLTHeaderId;
        this.projectId = projectId;
        this.rdmId = rdmId;
        this.rdmName = rdmName;
        this.analysisId = analysisId;
        this.peqtFilename = peqtFilename;
        this.importId = importId;
    }
}
