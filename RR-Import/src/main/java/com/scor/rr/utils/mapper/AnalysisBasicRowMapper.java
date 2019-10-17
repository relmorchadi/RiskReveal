package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.rms.RmsAnalysisBasic;
import com.scor.rr.utils.ALMFUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AnalysisBasic Row Mapper
 *
 * @author HADDINI Zakariyae
 */
public class AnalysisBasicRowMapper implements RowMapper<RmsAnalysisBasic> {

    /**
     * {@inheritDoc}
     */
    public RmsAnalysisBasic mapRow(ResultSet rs, int rowNum) throws SQLException {
        RmsAnalysisBasic rmsAnalysisBasic = new RmsAnalysisBasic();

        if (ALMFUtils.isNotNull(rs))
            fillRmsAnalysisBasicFields(rs, rmsAnalysisBasic);

        return rmsAnalysisBasic;
    }

    /**
     * fill RmsAnalysisBasic fields
     *
     * @param rs
     * @param rmsAnalysisBasic
     * @throws SQLException
     */
    private void fillRmsAnalysisBasicFields(ResultSet rs, RmsAnalysisBasic rmsAnalysisBasic) throws SQLException {
        rmsAnalysisBasic.setRdmId(rs.getLong("rdm_id"));

        rmsAnalysisBasic.setRdmName(rs.getString("rdm_name"));

        rmsAnalysisBasic.setAnalysisId(rs.getLong("analysis_id"));

        rmsAnalysisBasic.setAnalysisName(rs.getString("analysis_name"));

        rmsAnalysisBasic.setDescription(rs.getString("analysis_description"));

        rmsAnalysisBasic.setEngineVersion(rs.getString("engine_version"));

        rmsAnalysisBasic.setGroupTypeName(rs.getString("groupType_name"));

        rmsAnalysisBasic.setCedant(rs.getString("cedant"));

        rmsAnalysisBasic.setLobName(rs.getString("lob_name"));

        rmsAnalysisBasic.setGrouping(rs.getBoolean("is_grouping"));

        rmsAnalysisBasic.setEngineType(rs.getString("engine_type"));

        rmsAnalysisBasic.setRunDate(rs.getDate("run_date"));

        rmsAnalysisBasic.setTypeName(rs.getString("type_name"));

        rmsAnalysisBasic.setPeril(rs.getString("peril"));

        rmsAnalysisBasic.setSubPeril(rs.getString("sub_peril"));

        rmsAnalysisBasic.setLossAmplification(rs.getString("loss_amplification"));

        rmsAnalysisBasic.setRegion(rs.getString("region"));

        rmsAnalysisBasic.setRegionName(rs.getString("region_name"));

        rmsAnalysisBasic.setModeName(rs.getString("mode_name"));

        rmsAnalysisBasic.setUser1(rs.getString("user1"));

        rmsAnalysisBasic.setUser2(rs.getString("user2"));

        rmsAnalysisBasic.setUser3(rs.getString("user3"));

        rmsAnalysisBasic.setUser4(rs.getString("user4"));

        rmsAnalysisBasic.setAnalysisCurrency(rs.getString("analysis_ccy"));

        rmsAnalysisBasic.setStatusDescription(rs.getString("status_description"));
    }

}
