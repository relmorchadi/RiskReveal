package com.scor.rr.mapper;
import com.scor.rr.domain.AnalysisSummaryStats;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalysisSummaryStatsRowMapper implements RowMapper<AnalysisSummaryStats> {

    @Override
    public AnalysisSummaryStats mapRow(ResultSet rs, int rowNum) throws SQLException {

        AnalysisSummaryStats analysisSummaryStats = new AnalysisSummaryStats();

        analysisSummaryStats.setAnalysisId(rs.getLong("analysis_id"));
        analysisSummaryStats.setFpCode(rs.getString("fin_persp_code"));
        analysisSummaryStats.setEpTypeCode(rs.getInt("ep_type_code"));
        analysisSummaryStats.setPurePremium(BigDecimal.valueOf(rs.getDouble("pure_premium")).setScale(7, RoundingMode.HALF_UP).doubleValue());
        analysisSummaryStats.setStdDev(BigDecimal.valueOf(rs.getDouble("std_dev")).setScale(7, RoundingMode.HALF_UP).doubleValue());
        analysisSummaryStats.setCov(BigDecimal.valueOf(rs.getDouble("cov")).setScale(14, RoundingMode.HALF_UP).doubleValue());
        return analysisSummaryStats;
    }
}
