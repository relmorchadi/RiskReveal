package com.scor.rr.util;

import com.scor.rr.domain.RdmAnalysisBasic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;


public class RdmAnalysisBasicRowMapper implements RowMapper<RdmAnalysisBasic> {

    @Override
    public RdmAnalysisBasic mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAnalysisBasic rdmAnalysisBasic = new RdmAnalysisBasic();

        rdmAnalysisBasic.setRdmId(rs.getLong("rdm_id"));
        rdmAnalysisBasic.setRdmName(rs.getString("rdm_name"));
        rdmAnalysisBasic.setAnalysisId(rs.getLong("analysis_id"));
        rdmAnalysisBasic.setAnalysisName(rs.getString("analysis_name"));
        rdmAnalysisBasic.setDescription(rs.getString("analysis_description"));
        rdmAnalysisBasic.setEngineVersion(rs.getString("engine_version"));
        rdmAnalysisBasic.setGroupTypeName(rs.getString("groupType_name"));
        rdmAnalysisBasic.setCedant(rs.getString("cedant"));
        rdmAnalysisBasic.setLobName(rs.getString("lob_name"));
        rdmAnalysisBasic.setGrouping(rs.getBoolean("is_grouping"));
        rdmAnalysisBasic.setEngineType(rs.getString("engine_type"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        rdmAnalysisBasic.setRunDate(df.format(rs.getTimestamp("run_date").toLocalDateTime()));
        rdmAnalysisBasic.setTypeName(rs.getString("type_name"));
        rdmAnalysisBasic.setPeril(rs.getString("peril"));
        rdmAnalysisBasic.setSubPeril(rs.getString("sub_peril"));
        rdmAnalysisBasic.setLossAmplification(rs.getString("loss_amplification"));
        rdmAnalysisBasic.setRegion(rs.getString("region"));
        rdmAnalysisBasic.setRegionName(rs.getString("region_name"));
        rdmAnalysisBasic.setModeName(rs.getString("mode_name"));
        rdmAnalysisBasic.setUser1(rs.getString("user1"));
        rdmAnalysisBasic.setUser2(rs.getString("user2"));
        rdmAnalysisBasic.setUser3(rs.getString("user3"));
        rdmAnalysisBasic.setUser4(rs.getString("user4"));
        rdmAnalysisBasic.setAnalysisCurrency(rs.getString("analysis_ccy"));
        rdmAnalysisBasic.setStatusDescription(rs.getString("status_description"));

        return rdmAnalysisBasic;

    }
}
