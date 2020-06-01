package com.scor.rr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "LossDataHeader")
public class LossDataHeaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LossDataHeaderId")
    private Long lossDataHeaderId;
    @Column(name = "Entity")
    private Long entity;
    @Column(name = "ModelAnalysisId")
    private Long modelAnalysisId;
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
    @Column(name = "CloningSourceId")
    private Long CloningSourceId;

    public LossDataHeaderEntity(LossDataHeaderEntity other) {
        this.entity = other.entity;
        this.modelAnalysisId = other.modelAnalysisId;
        this.lossTableType = other.lossTableType;
        this.originalTarget = other.originalTarget;
        this.currency = other.currency;
        this.createdDate = other.createdDate;
        this.lossDataFilePath = other.lossDataFilePath;
        this.lossDataFileName = other.lossDataFileName;
        this.fileDataFormat = other.fileDataFormat;
        this.fileType = other.fileType;
        this.CloningSourceId = other.CloningSourceId;

    }

}
