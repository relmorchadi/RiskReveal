package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.exposuresummary.RmsExposureSummaryItem;
import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Rms Exposure Summary Item Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class RmsExposureSummaryItemMapper implements RowMapper<RmsExposureSummaryItem> {

	private Long edmId;

	public RmsExposureSummaryItemMapper(Long edmId) {
		this.edmId = edmId;
	}

	/**
	 * {@inheritDoc}
	 */
	public RmsExposureSummaryItem mapRow(ResultSet rs, int line) throws SQLException {
		RmsExposureSummaryItem rmsExposureSummaryItem = new RmsExposureSummaryItem();

		if (ALMFUtils.isNotNull(rs))
			fillRmsExposureSummaryItemFields(rs, rmsExposureSummaryItem);

		return rmsExposureSummaryItem;
	}

	/**
	 * fill RmsExposureSummaryItem fields
	 * 
	 * @param rs
	 * @param rmsExposureSummaryItem
	 * @throws SQLException
	 */
	private void fillRmsExposureSummaryItemFields(ResultSet rs, RmsExposureSummaryItem rmsExposureSummaryItem)
			throws SQLException {
		rmsExposureSummaryItem.setEdmId(edmId);

		rmsExposureSummaryItem.setPortfolioId(rs.getLong("port_id"));

		rmsExposureSummaryItem.setPortfolioType(rs.getString("port_type"));

		rmsExposureSummaryItem.setSummaryName(rs.getString("SummaryName"));

		rmsExposureSummaryItem.setFinancialPerspective(rs.getString("FinancialPerspective"));

		rmsExposureSummaryItem.setPeril(rs.getString("Peril"));

		rmsExposureSummaryItem.setAdmin1Code(StringUtils.trim(rs.getString("Admin1Code")));

		rmsExposureSummaryItem.setCountryCode(rs.getString("CountryCode"));

		rmsExposureSummaryItem.setDimension1(rs.getString("DimensionValue1"));

		rmsExposureSummaryItem.setDimension2(rs.getString("DimensionValue2"));

		rmsExposureSummaryItem.setDimension3(rs.getString("DimensionValue3"));

		rmsExposureSummaryItem.setDimension4(rs.getString("DimensionValue4"));

		rmsExposureSummaryItem.setDimensionSort1(rs.getInt("DimensionSort1"));

		rmsExposureSummaryItem.setDimensionSort2(rs.getInt("DimensionSort2"));

		rmsExposureSummaryItem.setDimensionSort3(rs.getInt("DimensionSort3"));

		rmsExposureSummaryItem.setDimensionSort4(rs.getInt("DimensionSort4"));

		rmsExposureSummaryItem.setLocationCount(rs.getLong("LocCount"));

		rmsExposureSummaryItem.setTotalTiv(rs.getDouble("TotalTIV"));

		rmsExposureSummaryItem.setExposureCurrency(rs.getString("Expo_Ccy"));

		rmsExposureSummaryItem.setExposureCurrencyUSDRate(rs.getDouble("Expo_Ccy_Roe_USD"));

		rmsExposureSummaryItem.setConformedCurrency(rs.getString("Conformed_Ccy"));

		rmsExposureSummaryItem.setConformedCurrencyUSDRate(rs.getDouble("Conformed_Ccy_ROE_USD"));

		rmsExposureSummaryItem.setRateDate(rs.getDate("RoE_AsAtDate"));
	}

}