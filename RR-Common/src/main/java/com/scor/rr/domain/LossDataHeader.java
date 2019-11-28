package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LossDataHeader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LossDataHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LossDataHeaderId")
    private Long lossDataHeaderId;

    @Column(name = "Entity")
    private Integer entity;

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
    private Long cloningSourceId;

}
