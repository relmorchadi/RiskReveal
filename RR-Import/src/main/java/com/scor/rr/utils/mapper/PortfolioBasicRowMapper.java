package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RmsPortfolioBasic;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PortfolioBasic Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class PortfolioBasicRowMapper implements RowMapper<RmsPortfolioBasic> {

	/**
	 * {@inheritDoc}
	 */
	public RmsPortfolioBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
		RmsPortfolioBasic rmsPortfolioBasic = new RmsPortfolioBasic();

		if (ALMFUtils.isNotNull(rs))
			fillRmsPortfolioBasicFields(rs, rmsPortfolioBasic);

		return rmsPortfolioBasic;
	}

	/**
	 * fill RmsPortfolioBasic fields
	 * 
	 * @param rs
	 * @param rmsPortfolioBasic
	 * @throws SQLException
	 */
	private void fillRmsPortfolioBasicFields(ResultSet rs, RmsPortfolioBasic rmsPortfolioBasic) throws SQLException {
		rmsPortfolioBasic.setEdmId(rs.getLong("edm_id"));

		rmsPortfolioBasic.setEdmName(rs.getString("edm_name"));

		rmsPortfolioBasic.setPortfolioId(rs.getLong("port_id"));

		rmsPortfolioBasic.setNumber(rs.getString("port_num"));

		rmsPortfolioBasic.setName(rs.getString("port_name"));

		rmsPortfolioBasic.setCreated(rs.getDate("port_created_dt"));

		rmsPortfolioBasic.setDescription(rs.getString("port_descr"));

		rmsPortfolioBasic.setType(rs.getString("port_type"));

		rmsPortfolioBasic.setPeril(rs.getString("peril"));

		rmsPortfolioBasic.setAgSource(rs.getString("ag_source"));

		rmsPortfolioBasic.setAgCedent(rs.getString("ag_cedant"));

		rmsPortfolioBasic.setAgCurrency(rs.getString("ag_ccy"));
	}

}
