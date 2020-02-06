package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLAnalysisDto {

    private Long rlAnalysisId;
    private Long rlId;
    private Long rdmId;
    private String rdmName;
    private String analysisName;
    private String analysisDescription;
    private String engineType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date runDate;
    private String analysisType;
    private String peril;
    private String subPeril;
    private String lossAmplification;
    private String region;
    private String analysisMode;
    private String analysisCurrency;
}
