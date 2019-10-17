package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RMSELTLoss;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RmsAnalysisELT Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class RmsAnalysisELTMapper implements RowMapper<RMSELTLoss> {

	/**
	 * {@inheritDoc}
	 */
	public RMSELTLoss mapRow(ResultSet rs, int arg1) throws SQLException {
		RMSELTLoss rmseltLoss = new RMSELTLoss();

		if (ALMFUtils.isNotNull(rs))
			fillRmsELTLossFields(rs, rmseltLoss);

		return rmseltLoss;
	}

	/**
	 * fill RmsELTLoss fields
	 * 
	 * @param rs
	 * @param rmseltLoss
	 * @throws SQLException
	 */
	private void fillRmsELTLossFields(ResultSet rs, RMSELTLoss rmseltLoss) throws SQLException {
		rmseltLoss.setEventId(rs.getLong("event_id"));

		rmseltLoss.setRate(rs.getDouble("rate"));

		rmseltLoss.setLoss(rs.getDouble("loss"));

		rmseltLoss.setStdDevI(rs.getDouble("std_dev_i"));

		rmseltLoss.setStdDevC(rs.getDouble("std_dev_c"));

		rmseltLoss.setExposureValue(rs.getDouble("exposure_value"));
	}
	
}