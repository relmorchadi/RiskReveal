package com.scor.rr.util;

import com.scor.rr.domain.RdmAllAnalysisTreatyStructure;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RdmAllAnalysisTreatyStructureRowMapper implements RowMapper<RdmAllAnalysisTreatyStructure> {

    @Override
    public RdmAllAnalysisTreatyStructure mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAllAnalysisTreatyStructure rdmAllAnalysisTreatyStructure = new RdmAllAnalysisTreatyStructure();

        rdmAllAnalysisTreatyStructure.setAnalysisId(rs.getInt("analysis_id"));
        rdmAllAnalysisTreatyStructure.setTreatyId(rs.getInt("treaty_id"));
        rdmAllAnalysisTreatyStructure.setTreatyNum(rs.getString("treaty_num"));
        rdmAllAnalysisTreatyStructure.setTreatyName(rs.getString("treaty_name"));
        rdmAllAnalysisTreatyStructure.setTreatyType(rs.getString("treaty_type"));
        rdmAllAnalysisTreatyStructure.setRiskLimit(rs.getLong("risk_limit"));
        rdmAllAnalysisTreatyStructure.setOccurenceLimit(rs.getLong("occurrence_limit"));
        rdmAllAnalysisTreatyStructure.setAttachmentPoint(rs.getLong("attachment_point"));
        rdmAllAnalysisTreatyStructure.setLob(rs.getString("lob"));
        rdmAllAnalysisTreatyStructure.setCedant(rs.getString("cedant"));
        rdmAllAnalysisTreatyStructure.setPctCovered(rs.getLong("pct_covered"));
        rdmAllAnalysisTreatyStructure.setPctPlaced(rs.getLong("pct_placed"));
        rdmAllAnalysisTreatyStructure.setPctRiShared(rs.getLong("pct_ri_share"));
        rdmAllAnalysisTreatyStructure.setPctRetention(rs.getLong("pct_retention"));
        rdmAllAnalysisTreatyStructure.setNoofReinstatements(rs.getInt("noof_reinstatements"));
        rdmAllAnalysisTreatyStructure.setInuringPriority(rs.getInt("inuring_priority"));
        rdmAllAnalysisTreatyStructure.setCcyCode(rs.getString("ccy_code"));
        rdmAllAnalysisTreatyStructure.setAttachementBasis(rs.getString("attachment_basis"));
        rdmAllAnalysisTreatyStructure.setExposureLevel(rs.getString("exposure_level"));

        return rdmAllAnalysisTreatyStructure;

    }
}
