package com.scor.rr.service.params;

import com.scor.rr.domain.entities.ihub.RRPortfolio;
import com.scor.rr.domain.entities.omega.Contract;
import com.scor.rr.domain.entities.omega.Section;
import com.scor.rr.domain.entities.workspace.Project;
import com.scor.rr.utils.ALMFUtils;

/**
 * ExposureFileParam
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ExposureFileParam {

	private String createdBy;

	private Contract contract;
	private Project project;
	private Section section;
	private RRPortfolio rrPortfolio;

	public ExposureFileParam(Contract contract, Project project, Section section, RRPortfolio rrPortfolio) {
		// @formatter:off
		this.createdBy = 
				ALMFUtils.isNotNull(project.getCreatedBy())
				? project
						.getCreatedBy()
						.getFirstName()
						.concat(" ")
						.concat(project
								.getCreatedBy()
								.getLastName())
				: "";
		// @formatter:on

		this.contract = contract;
		this.project = project;
		this.section = section;
		this.rrPortfolio = rrPortfolio;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public RRPortfolio getRrPortfolio() {
		return rrPortfolio;
	}

	public void setRrPortfolio(RRPortfolio rrPortfolio) {
		this.rrPortfolio = rrPortfolio;
	}

}
