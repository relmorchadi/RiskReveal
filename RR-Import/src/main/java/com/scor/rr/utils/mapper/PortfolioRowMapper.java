package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RmsPortfolio;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Portfolio Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PortfolioRowMapper implements RowMapper<RmsPortfolio> {

	public PortfolioRowMapper() {
	}

	/**
	 * {@inheritDoc}
	 */
	public RmsPortfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
		RmsPortfolio rmsPortfolio = new RmsPortfolio();

		if (ALMFUtils.isNotNull(rs))
			fillRmsPortfolioFields(rs, rmsPortfolio);

		return rmsPortfolio;
	}

	/**
	 * fill RmsPortfolio Fields
	 * 
	 * @param rs
	 * @param rmsPortfolio
	 * @throws SQLException
	 */
	private void fillRmsPortfolioFields(ResultSet rs, RmsPortfolio rmsPortfolio) throws SQLException {
		rmsPortfolio.setEdmId(rs.getLong("edm_id"));

		rmsPortfolio.setEdmName(rs.getString("edm_name"));

		rmsPortfolio.setPortfolioId(rs.getString("port_id"));

		rmsPortfolio.setNumber(rs.getString("port_num"));

		rmsPortfolio.setName(rs.getString("port_name"));

		rmsPortfolio.setCreated(ALMFUtils.translateDate(rs.getTimestamp("port_created_dt")));

		rmsPortfolio.setDescription(rs.getString("port_descr"));

		rmsPortfolio.setType(rs.getString("port_type"));

		rmsPortfolio.setPeril(rs.getString("peril"));

		rmsPortfolio.setAgSource(rs.getString("ag_source"));

		rmsPortfolio.setAgCedent(rs.getString("ag_cedant"));

		rmsPortfolio.setAgCurrency(rs.getString("ag_ccy"));

		rmsPortfolio.setTiv(rs.getBigDecimal("tiv"));
	}

}