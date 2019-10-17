package com.scor.rr.importBatch.processing.workflow.io;

import com.scor.rr.domain.entities.workspace.Portfolio;

import java.util.ArrayList;
import java.util.List;

/**
 * Portfolio Exposure Summary Extract Input
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PortfolioExposureSummaryExtractInput {

	private Portfolio portfolio;
	private String conformedCurrency = "EUR";
	private List<String> regionPerilToExclude = new ArrayList<>();

	public PortfolioExposureSummaryExtractInput(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public PortfolioExposureSummaryExtractInput(Portfolio portfolio, String conformedCurrency) {
		this.portfolio = portfolio;
		this.conformedCurrency = conformedCurrency;
	}

	public PortfolioExposureSummaryExtractInput(Portfolio portfolio, String conformedCurrency,
			List<String> regionPerilToExclude) {
		this.portfolio = portfolio;
		this.conformedCurrency = conformedCurrency;
		this.regionPerilToExclude = regionPerilToExclude;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public String getConformedCurrency() {
		return conformedCurrency;
	}

	public void setConformedCurrency(String conformedCurrency) {
		this.conformedCurrency = conformedCurrency;
	}

	public List<String> getRegionPerilToExclude() {
		return regionPerilToExclude;
	}

	public void setRegionPerilToExclude(List<String> regionPerilToExclude) {
		this.regionPerilToExclude = regionPerilToExclude;
	}

}
