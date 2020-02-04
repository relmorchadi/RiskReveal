package com.scor.rr.mapper;

import com.scor.rr.domain.RdmAnalysis;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class RdmAnalysisRowMapper implements RowMapper<RdmAnalysis> {

    @Override
    public RdmAnalysis mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAnalysis rdmAnalysis = new RdmAnalysis();

        rdmAnalysis.setRdmId(rs.getLong("rdm_id"));
        rdmAnalysis.setRdmName(rs.getString("rdm_name"));
        rdmAnalysis.setAnalysisId(rs.getLong("analysis_id"));
        rdmAnalysis.setAnalysisName(rs.getString("analysis_name"));
        rdmAnalysis.setDescription(rs.getString("analysis_description"));
        rdmAnalysis.setDefaultGrain(rs.getString("default_grain"));
        rdmAnalysis.setExposureType(rs.getString("exposure_type"));
        rdmAnalysis.setExposureTypeCode(rs.getInt("exposuretype_code"));
        rdmAnalysis.setEdmNameSourceLink(rs.getString("source_edm_name"));
        rdmAnalysis.setExposureId(rs.getLong("exposure_id"));
        rdmAnalysis.setAnalysisCurrency(rs.getString("analysis_ccy"));
        rdmAnalysis.setRmsExchangeRate(rs.getBigDecimal("ccy_rate"));
        rdmAnalysis.setTypeCode(rs.getInt("type_code"));
        rdmAnalysis.setAnalysisType(rs.getString("analysis_type"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        rdmAnalysis.setRunDate(df.format(rs.getTimestamp("run_date").toLocalDateTime()));
        rdmAnalysis.setRegion(rs.getString("region"));
        rdmAnalysis.setPeril(rs.getString("peril"));
        rdmAnalysis.setRpCode(rs.getString("region_peril"));
        rdmAnalysis.setSubPeril(rs.getString("sub_peril"));
        rdmAnalysis.setLossAmplification(rs.getString("loss_amplification"));
        rdmAnalysis.setStatus(rs.getLong("status"));
        // TODO : Waiting for huw's feedback (basic = distributed) != (detailed = 2)
        rdmAnalysis.setAnalysisMode(rs.getString("analysis_mode"));
        rdmAnalysis.setEngineTypeCode(rs.getInt("enginetype_code"));
        rdmAnalysis.setEngineType(rs.getString("engine_type"));
        rdmAnalysis.setEngineVersion(rs.getString("engine_version"));
        rdmAnalysis.setEngineVersionMajor(rs.getString("engine_version_major"));
        rdmAnalysis.setProfileName(rs.getString("profile_name"));
        rdmAnalysis.setProfileKey(rs.getString("profile_key"));
        rdmAnalysis.setHasMultiRegionPerils(rs.getBoolean("has_multi_region_perils"));
        rdmAnalysis.setValidForExtract(rs.getBoolean("is_valid_for_extract"));
        rdmAnalysis.setNotValidReason(rs.getString("not_valid_for_extract_reason"));
        rdmAnalysis.setPurePremium(rs.getBigDecimal("pure_premium"));
        rdmAnalysis.setExposureTiv(rs.getDouble("exposure_tiv"));
        rdmAnalysis.setGeoCode(rs.getString("geo_code"));
        rdmAnalysis.setGeoDescription(rs.getString("geo_description"));
        rdmAnalysis.setUser1(rs.getString("user1"));
        rdmAnalysis.setUser2(rs.getString("user2"));
        rdmAnalysis.setUser3(rs.getString("user3"));
        rdmAnalysis.setUser4(rs.getString("user4"));

        return rdmAnalysis ;
    }
}
