package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RmsAnalysis;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Analysis Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class AnalysisRowMapper implements RowMapper<RmsAnalysis> {

	/**
	 * {@inheritDoc}
	 */
	public RmsAnalysis mapRow(ResultSet rs, int rowNum) throws SQLException {
		RmsAnalysis rmsAnalysis = new RmsAnalysis();

		if (ALMFUtils.isNotNull(rs))
			fillRmsAnalysisFields(rs, rmsAnalysis);

		return rmsAnalysis;
	}

	/**
	 * fill RmsAnalysis fields
	 * 
	 * @param rs
	 * @param rmsAnalysis
	 * @throws SQLException
	 */
	private void fillRmsAnalysisFields(ResultSet rs, RmsAnalysis rmsAnalysis) throws SQLException {
		rmsAnalysis.setRdmId(rs.getLong("rdm_id"));

		rmsAnalysis.setRdmName(rs.getString("rdm_name"));

		rmsAnalysis.setAnalysisId(rs.getString("analysis_id"));

		rmsAnalysis.setAnalysisName(rs.getString("analysis_name"));

		rmsAnalysis.setDescription(rs.getString("analysis_description"));

		rmsAnalysis.setDefaultGrain(rs.getString("default_grain"));

		rmsAnalysis.setExposureType(rs.getString("exposure_type"));

		rmsAnalysis.setExposureTypeCode(rs.getInt("exposuretype_code"));

		rmsAnalysis.setEdmNameSourceLink(rs.getString("source_edm_name"));

		rmsAnalysis.setExposureId(rs.getLong("exposure_id"));

		rmsAnalysis.setAnalysisCurrency(rs.getString("analysis_ccy"));

		rmsAnalysis.setRmsExchangeRate(rs.getBigDecimal("ccy_rate"));

		rmsAnalysis.setTypeCode(rs.getInt("type_code"));

		rmsAnalysis.setAnalysisType(rs.getString("analysis_type"));

		rmsAnalysis.setRunDate(rs.getDate("run_date"));

		rmsAnalysis.setRegion(rs.getString("region"));

		rmsAnalysis.setPeril(rs.getString("peril"));

		rmsAnalysis.setRpCode(rs.getString("region_peril"));

		rmsAnalysis.setSubPeril(rs.getString("sub_peril"));

		rmsAnalysis.setLossAmplification(rs.getString("loss_amplification"));

		rmsAnalysis.setStatus(rs.getLong("status"));

		rmsAnalysis.setAnalysisMode(rs.getInt("analysis_mode"));

		rmsAnalysis.setEngineTypeCode(rs.getInt("enginetype_code"));

		rmsAnalysis.setEngineType(rs.getString("engine_type"));

		rmsAnalysis.setEngineVersion(rs.getString("engine_version"));

		rmsAnalysis.setEngineVersionMajor(rs.getString("engine_version_major"));

		rmsAnalysis.setProfileName(rs.getString("profile_name"));

		rmsAnalysis.setProfileKey(rs.getString("profile_key"));

		rmsAnalysis.setIsValidForExtract(rs.getBoolean("is_valid_for_extract"));

		rmsAnalysis.setNotValidReason(rs.getString("not_valid_for_extract_reason"));

		rmsAnalysis.setPurePremium(rs.getBigDecimal("pure_premium"));

		rmsAnalysis.setExposureTiv(rs.getDouble("exposure_tiv"));

		rmsAnalysis.setGeoCode(rs.getString("geo_code"));

		rmsAnalysis.setGeoDescription(rs.getString("geo_description"));

		rmsAnalysis.setUser1(rs.getString("user1"));

		rmsAnalysis.setUser2(rs.getString("user2"));

		rmsAnalysis.setUser3(rs.getString("user3"));

		rmsAnalysis.setUser4(rs.getString("user4"));

		// tmp.setMultipleRegionPerils(rs.getBoolean("has_multi_region_perils"));
	}
}
