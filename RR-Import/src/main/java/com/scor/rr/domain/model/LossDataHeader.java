package com.scor.rr.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class LossDataHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lossDataHeaderId;
    private Long entity;
    private Long modelAnalysisId;
    private String lossTableType;
    private String originalTarget;
    private String currency;
    private Date createdDate;
    private String lossDataFilePath;
    private String lossDataFileName;
    private String fileDataFormat;
    private String fileType;
    private Long CloningSourceId;

}
