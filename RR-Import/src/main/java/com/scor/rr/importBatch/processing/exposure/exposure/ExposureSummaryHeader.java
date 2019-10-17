package com.scor.rr.importBatch.processing.exposure.exposure;

import com.scor.rr.domain.entities.meta.exposure.ExposureSummaryExtractFile;
import com.scor.rr.domain.enums.ExposureSummaryExtractType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TTExposureSummaryHeader")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureSummaryHeader {
    @Id
    private String id;
    private String projectId; //from projectId we can find projectName, description, creationDate, createdBy
    private String sourceVendorModel;
    private Long modelSystemVersion;
    private String financialPerspective;
    private String currency;
    private String edmId;
    private String edmName;
    private Long portfolioId;
    private String portfolioName;
    private ExposureSummaryExtractType extractType;
    private Date creationDate;
    private String createBy;
    private Boolean isPublished;
    private Date publishDate;
    private String publishBy;
    // TODO: To Review
    @Transient
    private List<ExposureSummaryExtractFile> extractFiles;
}
