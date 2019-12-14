package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ModelPortfolioStorage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelPortfolioStorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelPortfolioStorageId")
    private Long modelPortfolioStorageId;
    @Column(name = "ProjectId")
    private Long projectId;
    @Column(name = "ModelPortfolioId")
    private Long modelPortfolioId;
    @Column(name = "DataType")
    private String dataType;
    @Column(name = "DataSubType")
    private String DataSubType;
    @Column(name = "Currency")
    private String currency;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "FilePath")
    private String filePath;
    @Column(name = "Published")
    private boolean published;
    @Column(name = "PublishedDate")
    private Date publishedDate;
    @Column(name = "PublishedBy")
    private String publishedBy;
}
