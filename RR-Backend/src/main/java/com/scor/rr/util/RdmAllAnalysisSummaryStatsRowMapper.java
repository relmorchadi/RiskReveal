package com.scor.rr.util;

import com.scor.rr.domain.RdmAllAnalysisSummaryStats;
import org.springframework.jdbc.core.RowMapper;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RdmAllAnalysisSummaryStatsRowMapper implements RowMapper<RdmAllAnalysisSummaryStats> {


    @Override
    public RdmAllAnalysisSummaryStats mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAllAnalysisSummaryStats rdmAllAnalysisSummaryStats = new RdmAllAnalysisSummaryStats();

        rdmAllAnalysisSummaryStats.setAnalysisId(rs.getLong("analysis_id"));
        rdmAllAnalysisSummaryStats.setFinPerspCode(rs.getString("fin_persp_code"));
        rdmAllAnalysisSummaryStats.setTreatyLabelId(rs.getInt("treaty_label_id"));
        rdmAllAnalysisSummaryStats.setTreatyLabel(rs.getString("treaty_label"));
        rdmAllAnalysisSummaryStats.setTreatyTyepCode(rs.getString("treaty_type_code"));
        rdmAllAnalysisSummaryStats.setTreatyType(rs.getString("treaty_type"));
        rdmAllAnalysisSummaryStats.setTreatyTag(rs.getString("treaty_tag"));
        rdmAllAnalysisSummaryStats.setOccurrenceBasis(rs.getString("occurrence_basis"));
        rdmAllAnalysisSummaryStats.setEpTypeCode(rs.getInt("ep_type_code"));
        rdmAllAnalysisSummaryStats.setPurePremium(rs.getBigDecimal("pure_premium").setScale(7, RoundingMode.HALF_UP));
        rdmAllAnalysisSummaryStats.setStdDev(rs.getBigDecimal("std_dev").setScale(7, RoundingMode.HALF_UP));
        rdmAllAnalysisSummaryStats.setCov(rs.getBigDecimal("cov").setScale(14, RoundingMode.HALF_UP));

        return rdmAllAnalysisSummaryStats;
    }
}
