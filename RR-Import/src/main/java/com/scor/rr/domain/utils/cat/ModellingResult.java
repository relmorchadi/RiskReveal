package com.scor.rr.domain.utils.cat;

import com.scor.rr.domain.utils.plt.PLT;
import com.scor.rr.domain.utils.plt.ELT;
import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.plt.ModelRAPSource;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.references.plt.FinancialPerspective;

/**
 * ModellingResult
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ModellingResult {

	private Boolean isPartial;
	private String dlmFileName;
	private Integer locationCount;
	private Double tivExposureValue;
	private String grainDescription;

	private ELT elt;
	private PLT purePLT;
	private PLT adjustedPLT;
	private ModelRAP modelRAP;
	private RegionPeril regionPeril;
	private Currency analysisCurrency;
	private ModelRAPSource modelRAPSource;
	private FinancialPerspective financialPerspectiveELT;
	private FinancialPerspective financialPerspectiveStats;

	public Boolean getIsPartial() {
		return isPartial;
	}

	public void setIsPartial(Boolean isPartial) {
		this.isPartial = isPartial;
	}

	public String getDlmFileName() {
		return dlmFileName;
	}

	public void setDlmFileName(String dlmFileName) {
		this.dlmFileName = dlmFileName;
	}

	public Integer getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(Integer locationCount) {
		this.locationCount = locationCount;
	}

	public Double getTivExposureValue() {
		return tivExposureValue;
	}

	public void setTivExposureValue(Double tivExposureValue) {
		this.tivExposureValue = tivExposureValue;
	}

	public String getGrainDescription() {
		return grainDescription;
	}

	public void setGrainDescription(String grainDescription) {
		this.grainDescription = grainDescription;
	}

	public ELT getElt() {
		return elt;
	}

	public void setElt(ELT elt) {
		this.elt = elt;
	}

	public PLT getPurePLT() {
		return purePLT;
	}

	public void setPurePLT(PLT purePLT) {
		this.purePLT = purePLT;
	}

	public PLT getAdjustedPLT() {
		return adjustedPLT;
	}

	public void setAdjustedPLT(PLT adjustedPLT) {
		this.adjustedPLT = adjustedPLT;
	}

	public ModelRAP getModelRAP() {
		return modelRAP;
	}

	public void setModelRAP(ModelRAP modelRAP) {
		this.modelRAP = modelRAP;
	}

	public RegionPeril getRegionPeril() {
		return regionPeril;
	}

	public void setRegionPeril(RegionPeril regionPeril) {
		this.regionPeril = regionPeril;
	}

	public Currency getAnalysisCurrency() {
		return analysisCurrency;
	}

	public void setAnalysisCurrency(Currency analysisCurrency) {
		this.analysisCurrency = analysisCurrency;
	}

	public ModelRAPSource getModelRAPSource() {
		return modelRAPSource;
	}

	public void setModelRAPSource(ModelRAPSource modelRAPSource) {
		this.modelRAPSource = modelRAPSource;
	}

	public FinancialPerspective getFinancialPerspectiveELT() {
		return financialPerspectiveELT;
	}

	public void setFinancialPerspectiveELT(FinancialPerspective financialPerspectiveELT) {
		this.financialPerspectiveELT = financialPerspectiveELT;
	}

	public FinancialPerspective getFinancialPerspectiveStats() {
		return financialPerspectiveStats;
	}

	public void setFinancialPerspectiveStats(FinancialPerspective financialPerspectiveStats) {
		this.financialPerspectiveStats = financialPerspectiveStats;
	}

}
