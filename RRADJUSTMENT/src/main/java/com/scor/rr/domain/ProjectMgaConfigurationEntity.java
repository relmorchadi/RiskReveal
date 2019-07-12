package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ProjectMGAConfiguration", schema = "dbo", catalog = "RiskReveal")
public class ProjectMgaConfigurationEntity {
    private int id;
    private String expectedFrequency;
    private String submissionPeriod;
    private String financialBasis;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "expectedFrequency", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getExpectedFrequency() {
        return expectedFrequency;
    }

    public void setExpectedFrequency(String expectedFrequency) {
        this.expectedFrequency = expectedFrequency;
    }

    @Basic
    @Column(name = "submissionPeriod", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSubmissionPeriod() {
        return submissionPeriod;
    }

    public void setSubmissionPeriod(String submissionPeriod) {
        this.submissionPeriod = submissionPeriod;
    }

    @Basic
    @Column(name = "financialBasis", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getFinancialBasis() {
        return financialBasis;
    }

    public void setFinancialBasis(String financialBasis) {
        this.financialBasis = financialBasis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMgaConfigurationEntity that = (ProjectMgaConfigurationEntity) o;
        return id == that.id &&
                Objects.equals(expectedFrequency, that.expectedFrequency) &&
                Objects.equals(submissionPeriod, that.submissionPeriod) &&
                Objects.equals(financialBasis, that.financialBasis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expectedFrequency, submissionPeriod, financialBasis);
    }
}
