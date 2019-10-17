package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RmsExchangeRate;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RmsExchangeRate Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class RmsExchangeRatesMapper implements RowMapper<RmsExchangeRate> {

	/**
	 * {@inheritDoc}
	 */
	public RmsExchangeRate mapRow(ResultSet rs, int arg1) throws SQLException {
		RmsExchangeRate rmsExchangeRate = new RmsExchangeRate();

		if (ALMFUtils.isNotNull(rs))
			fillRmsExchangeRateFields(rs, rmsExchangeRate);

		return rmsExchangeRate;
	}

	/**
	 * fill RmsExchangeRate fields
	 * 
	 * @param rs
	 * @param rmsExchangeRate
	 * @throws SQLException
	 */
	private void fillRmsExchangeRateFields(ResultSet rs, RmsExchangeRate rmsExchangeRate) throws SQLException {
		rmsExchangeRate.setCcy(rs.getString(1));

		rmsExchangeRate.setExchangeRate(rs.getDouble(2));

		rmsExchangeRate.setDate(rs.getDate(3));
	}
	
}
