package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RRLossDataHeader", schema = "dbo", catalog = "RiskReveal")
public class RrLossDataHeaderEntity {
    private int rrLossDataHeaderId;
    private String lossTableType;
    private String originalTarget;
    private String currency;
    private Timestamp createdDate;
    private String lossDataFilePath;
    private String lossDataFileName;
    private String fileDataFormat;
    private String fileType;
    private Integer cloningSourceId;

    @Id
    @Column(name = "rrLossDataHeaderId", nullable = false)
    public int getRrLossDataHeaderId() {
        return rrLossDataHeaderId;
    }

    public void setRrLossDataHeaderId(int rrLossDataHeaderId) {
        this.rrLossDataHeaderId = rrLossDataHeaderId;
    }

    @Basic
    @Column(name = "lossTableType", length = 255)
    public String getLossTableType() {
        return lossTableType;
    }

    public void setLossTableType(String lossTableType) {
        this.lossTableType = lossTableType;
    }

    @Basic
    @Column(name = "originalTarget", length = 255)
    public String getOriginalTarget() {
        return originalTarget;
    }

    public void setOriginalTarget(String originalTarget) {
        this.originalTarget = originalTarget;
    }

    @Basic
    @Column(name = "currency", length = 255)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "createdDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "lossDataFilePath", length = 255)
    public String getLossDataFilePath() {
        return lossDataFilePath;
    }

    public void setLossDataFilePath(String lossDataFilePath) {
        this.lossDataFilePath = lossDataFilePath;
    }

    @Basic
    @Column(name = "lossDataFileName", length = 255)
    public String getLossDataFileName() {
        return lossDataFileName;
    }

    public void setLossDataFileName(String lossDataFileName) {
        this.lossDataFileName = lossDataFileName;
    }

    @Basic
    @Column(name = "fileDataFormat", length = 255)
    public String getFileDataFormat() {
        return fileDataFormat;
    }

    public void setFileDataFormat(String fileDataFormat) {
        this.fileDataFormat = fileDataFormat;
    }

    @Basic
    @Column(name = "fileType", length = 255)
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "cloningSourceId")
    public Integer getCloningSourceId() {
        return cloningSourceId;
    }

    public void setCloningSourceId(Integer cloningSourceId) {
        this.cloningSourceId = cloningSourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrLossDataHeaderEntity that = (RrLossDataHeaderEntity) o;
        return rrLossDataHeaderId == that.rrLossDataHeaderId &&
                Objects.equals(lossTableType, that.lossTableType) &&
                Objects.equals(originalTarget, that.originalTarget) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(lossDataFilePath, that.lossDataFilePath) &&
                Objects.equals(lossDataFileName, that.lossDataFileName) &&
                Objects.equals(fileDataFormat, that.fileDataFormat) &&
                Objects.equals(fileType, that.fileType) &&
                Objects.equals(cloningSourceId, that.cloningSourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rrLossDataHeaderId, lossTableType, originalTarget, currency, createdDate, lossDataFilePath, lossDataFileName, fileDataFormat, fileType, cloningSourceId);
    }
}
