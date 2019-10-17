package com.scor.rr.domain.utils.plt;

import com.scor.rr.utils.ALMFUtils;

import java.util.Date;
import java.util.Objects;

/**
 * ELTSourceMetadata
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ELTSourceMetadata {

	private String sourceName;
	private String description;
	private String comment;
	private String originalCompanyName;
	private String originalProgram;
	private Date sourceImportDate;
	private Integer sourceAnalysisId;
	private String sourceAnalysisName;
	private String sourceAnalysisDescription;
	private Date sourceAnalysisRunDate;
	private String sourceAnalysisCurrency;
	private String sourceAnalysisCurrencyUnit;
	private String sourceAnalysisType;
	private String sourceAnalysisPeril;
	private String sourceAnalysisSubPeril;
	private String sourceAnalysisRegion;
	private String sourceAnalysisRegionPeril;
	private String sourceAnalysisFinancialPerspective;
	private String sourceAnalysisLossAmplification;
	private Integer sourceAnalysisScaleFactor;
	private Integer sourceAnalysisExposureID;
	private String sourceAnalysisExposureType;
	private String sourceAnalysisMode;
	private Boolean sourceAnalysisIsGroup;
	private String sourceAnalysisEventSet;

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOriginalCompanyName() {
		return originalCompanyName;
	}

	public void setOriginalCompanyName(String originalCompanyName) {
		this.originalCompanyName = originalCompanyName;
	}

	public String getOriginalProgram() {
		return originalProgram;
	}

	public void setOriginalProgram(String originalProgram) {
		this.originalProgram = originalProgram;
	}

	public Date getSourceImportDate() {
		return sourceImportDate;
	}

	public void setSourceImportDate(Date sourceImportDate) {
		this.sourceImportDate = sourceImportDate;
	}

	public Integer getSourceAnalysisId() {
		return sourceAnalysisId;
	}

	public void setSourceAnalysisId(Integer sourceAnalysisId) {
		this.sourceAnalysisId = sourceAnalysisId;
	}

	public String getSourceAnalysisName() {
		return sourceAnalysisName;
	}

	public void setSourceAnalysisName(String sourceAnalysisName) {
		this.sourceAnalysisName = sourceAnalysisName;
	}

	public String getSourceAnalysisDescription() {
		return sourceAnalysisDescription;
	}

	public void setSourceAnalysisDescription(String sourceAnalysisDescription) {
		this.sourceAnalysisDescription = sourceAnalysisDescription;
	}

	public Date getSourceAnalysisRunDate() {
		return sourceAnalysisRunDate;
	}

	public void setSourceAnalysisRunDate(Date sourceAnalysisRunDate) {
		this.sourceAnalysisRunDate = sourceAnalysisRunDate;
	}

	public String getSourceAnalysisCurrency() {
		return sourceAnalysisCurrency;
	}

	public void setSourceAnalysisCurrency(String sourceAnalysisCurrency) {
		this.sourceAnalysisCurrency = sourceAnalysisCurrency;
	}

	public String getSourceAnalysisCurrencyUnit() {
		return sourceAnalysisCurrencyUnit;
	}

	public void setSourceAnalysisCurrencyUnit(String sourceAnalysisCurrencyUnit) {
		this.sourceAnalysisCurrencyUnit = sourceAnalysisCurrencyUnit;
	}

	public String getSourceAnalysisType() {
		return sourceAnalysisType;
	}

	public void setSourceAnalysisType(String sourceAnalysisType) {
		this.sourceAnalysisType = sourceAnalysisType;
	}

	public String getSourceAnalysisPeril() {
		return sourceAnalysisPeril;
	}

	public void setSourceAnalysisPeril(String sourceAnalysisPeril) {
		this.sourceAnalysisPeril = sourceAnalysisPeril;
	}

	public String getSourceAnalysisSubPeril() {
		return sourceAnalysisSubPeril;
	}

	public void setSourceAnalysisSubPeril(String sourceAnalysisSubPeril) {
		this.sourceAnalysisSubPeril = sourceAnalysisSubPeril;
	}

	public String getSourceAnalysisRegion() {
		return sourceAnalysisRegion;
	}

	public void setSourceAnalysisRegion(String sourceAnalysisRegion) {
		this.sourceAnalysisRegion = sourceAnalysisRegion;
	}

	public String getSourceAnalysisRegionPeril() {
		return sourceAnalysisRegionPeril;
	}

	public void setSourceAnalysisRegionPeril(String sourceAnalysisRegionPeril) {
		this.sourceAnalysisRegionPeril = sourceAnalysisRegionPeril;
	}

	public String getSourceAnalysisFinancialPerspective() {
		return sourceAnalysisFinancialPerspective;
	}

	public void setSourceAnalysisFinancialPerspective(String sourceAnalysisFinancialPerspective) {
		this.sourceAnalysisFinancialPerspective = sourceAnalysisFinancialPerspective;
	}

	public String getSourceAnalysisLossAmplification() {
		return sourceAnalysisLossAmplification;
	}

	public void setSourceAnalysisLossAmplification(String sourceAnalysisLossAmplification) {
		this.sourceAnalysisLossAmplification = sourceAnalysisLossAmplification;
	}

	public Integer getSourceAnalysisScaleFactor() {
		return sourceAnalysisScaleFactor;
	}

	public void setSourceAnalysisScaleFactor(Integer sourceAnalysisScaleFactor) {
		this.sourceAnalysisScaleFactor = sourceAnalysisScaleFactor;
	}

	public Integer getSourceAnalysisExposureID() {
		return sourceAnalysisExposureID;
	}

	public void setSourceAnalysisExposureID(Integer sourceAnalysisExposureID) {
		this.sourceAnalysisExposureID = sourceAnalysisExposureID;
	}

	public String getSourceAnalysisExposureType() {
		return sourceAnalysisExposureType;
	}

	public void setSourceAnalysisExposureType(String sourceAnalysisExposureType) {
		this.sourceAnalysisExposureType = sourceAnalysisExposureType;
	}

	public String getSourceAnalysisMode() {
		return sourceAnalysisMode;
	}

	public void setSourceAnalysisMode(String sourceAnalysisMode) {
		this.sourceAnalysisMode = sourceAnalysisMode;
	}

	public Boolean getSourceAnalysisIsGroup() {
		return sourceAnalysisIsGroup;
	}

	public void setSourceAnalysisIsGroup(Boolean sourceAnalysisIsGroup) {
		this.sourceAnalysisIsGroup = sourceAnalysisIsGroup;
	}

	public String getSourceAnalysisEventSet() {
		return sourceAnalysisEventSet;
	}

	public void setSourceAnalysisEventSet(String sourceAnalysisEventSet) {
		this.sourceAnalysisEventSet = sourceAnalysisEventSet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sourceName, sourceImportDate, sourceAnalysisId, sourceAnalysisRunDate,
				sourceAnalysisRegionPeril, sourceAnalysisFinancialPerspective, sourceAnalysisEventSet);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass()))
			return false;

		ELTSourceMetadata other = (ELTSourceMetadata) obj;

		return Objects.equals(sourceName, other.sourceName) && Objects.equals(sourceImportDate, other.sourceImportDate)
				&& Objects.equals(sourceAnalysisId, other.sourceAnalysisId)
				&& Objects.equals(sourceAnalysisRunDate, other.sourceAnalysisRunDate)
				&& Objects.equals(sourceAnalysisRegionPeril, other.sourceAnalysisRegionPeril)
				&& Objects.equals(sourceAnalysisFinancialPerspective, other.sourceAnalysisFinancialPerspective)
				&& Objects.equals(sourceAnalysisEventSet, other.sourceAnalysisEventSet);
	}

}
