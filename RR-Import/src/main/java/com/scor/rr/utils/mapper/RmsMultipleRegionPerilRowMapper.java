package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RmsMultipleRegionPeril;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Rms Multiple Region Peril Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class RmsMultipleRegionPerilRowMapper implements RowMapper<RmsMultipleRegionPeril> {

	/**
	 * {@inheritDoc}
	 */
	public RmsMultipleRegionPeril mapRow(ResultSet rs, int rowNum) throws SQLException {
		RmsMultipleRegionPeril rmsMultipleRegionPeril = new RmsMultipleRegionPeril();

		if (ALMFUtils.isNotNull(rs))
			fillRmsMultipleRegionPerilFields(rs, rmsMultipleRegionPeril);

		return rmsMultipleRegionPeril;
	}

	/**
	 * fill RmsMultipleRegionPeril fields
	 * 
	 * @param rs
	 * @param rmsMultipleRegionPeril
	 * @throws SQLException
	 */
	private void fillRmsMultipleRegionPerilFields(ResultSet rs, RmsMultipleRegionPeril rmsMultipleRegionPeril)
			throws SQLException {
		rmsMultipleRegionPeril.setRdmId(rs.getLong("rdm_id"));

		rmsMultipleRegionPeril.setRdmName(rs.getString("rdm_name"));

		rmsMultipleRegionPeril.setAnlId(rs.getLong("analysis_id"));

		rmsMultipleRegionPeril.setRegion(rs.getString("ss_region"));

		rmsMultipleRegionPeril.setPeril(rs.getString("ss_peril"));

		rmsMultipleRegionPeril.setRegionPeril(rs.getString("ss_region_peril"));

		rmsMultipleRegionPeril.setProfileKey(rs.getString("profile_key"));

		rmsMultipleRegionPeril.setEvtCount(rs.getLong("ev_count"));
	}

}
