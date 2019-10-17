package com.scor.rr.domain.utils.plt.loss;

import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.references.plt.FinancialPerspective;
import com.scor.rr.domain.entities.references.plt.SubPeril;
import com.scor.rr.utils.ALMFUtils;

import java.util.Date;
import java.util.Objects;

/**
 * LossTableHeader
 * 
 * @author HADDINI Zakariyae
 *
 */
public class LossTableHeader {

	private Integer lossTableId;
	private Integer parentId;
	private Integer rootId;
	private Date createDate;
	private String clientName;
	private String clientId;
	private Integer uWYear;
	private String sourceFormatType;
	private String type;
	private String typeOrigin;
	private String basis;
	private Integer sourceSimulationYears;
	private Integer sourceSimulationPeriodBasis;
	private String modellingventSet;
	private String lossAmplification;
	private Boolean sourceHasEventRemapping;
	private Boolean sourceHasGrouping;
	private String groupingParentId;

	private User user;
	private Currency analysisCurrency;
	private FinancialPerspective financialPerspective;
	private RegionPeril regionPeril;
	private SubPeril subPeril;

	public LossTableHeader() {
	}

	public Integer getLossTableId() {
		return lossTableId;
	}

	public void setLossTableId(Integer lossTableId) {
		this.lossTableId = lossTableId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getuWYear() {
		return uWYear;
	}

	public void setuWYear(Integer uWYear) {
		this.uWYear = uWYear;
	}

	public String getSourceFormatType() {
		return sourceFormatType;
	}

	public void setSourceFormatType(String sourceFormatType) {
		this.sourceFormatType = sourceFormatType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeOrigin() {
		return typeOrigin;
	}

	public void setTypeOrigin(String typeOrigin) {
		this.typeOrigin = typeOrigin;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public Integer getSourceSimulationYears() {
		return sourceSimulationYears;
	}

	public void setSourceSimulationYears(Integer sourceSimulationYears) {
		this.sourceSimulationYears = sourceSimulationYears;
	}

	public Integer getSourceSimulationPeriodBasis() {
		return sourceSimulationPeriodBasis;
	}

	public void setSourceSimulationPeriodBasis(Integer sourceSimulationPeriodBasis) {
		this.sourceSimulationPeriodBasis = sourceSimulationPeriodBasis;
	}

	public String getModellingventSet() {
		return modellingventSet;
	}

	public void setModellingventSet(String modellingventSet) {
		this.modellingventSet = modellingventSet;
	}

	public String getLossAmplification() {
		return lossAmplification;
	}

	public void setLossAmplification(String lossAmplification) {
		this.lossAmplification = lossAmplification;
	}

	public Boolean getSourceHasEventRemapping() {
		return sourceHasEventRemapping;
	}

	public void setSourceHasEventRemapping(Boolean sourceHasEventRemapping) {
		this.sourceHasEventRemapping = sourceHasEventRemapping;
	}

	public Boolean getSourceHasGrouping() {
		return sourceHasGrouping;
	}

	public void setSourceHasGrouping(Boolean sourceHasGrouping) {
		this.sourceHasGrouping = sourceHasGrouping;
	}

	public String getGroupingParentId() {
		return groupingParentId;
	}

	public void setGroupingParentId(String groupingParentId) {
		this.groupingParentId = groupingParentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Currency getAnalysisCurrency() {
		return analysisCurrency;
	}

	public void setAnalysisCurrency(Currency analysisCurrency) {
		this.analysisCurrency = analysisCurrency;
	}

	public FinancialPerspective getFinancialPerspective() {
		return financialPerspective;
	}

	public void setFinancialPerspective(FinancialPerspective financialPerspective) {
		this.financialPerspective = financialPerspective;
	}

	public RegionPeril getRegionPeril() {
		return regionPeril;
	}

	public void setRegionPeril(RegionPeril regionPeril) {
		this.regionPeril = regionPeril;
	}

	public SubPeril getSubPeril() {
		return subPeril;
	}

	public void setSubPeril(SubPeril subPeril) {
		this.subPeril = subPeril;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lossTableId, parentId, rootId, createDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
			return false;
		}

		final LossTableHeader other = (LossTableHeader) obj;

		return ALMFUtils.isNotNull(other)
				? Objects.equals(this.lossTableId, other.lossTableId) && Objects.equals(this.parentId, other.parentId)
						&& Objects.equals(this.rootId, other.rootId)
						&& Objects.equals(this.createDate, other.createDate)
				: false;
	}

}
