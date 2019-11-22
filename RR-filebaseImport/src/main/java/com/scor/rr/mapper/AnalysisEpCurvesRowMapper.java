package com.scor.rr.mapper;

import com.scor.rr.domain.AnalysisEpCurves;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalysisEpCurvesRowMapper implements RowMapper<AnalysisEpCurves> {

    @Override
    public AnalysisEpCurves mapRow(ResultSet rs , int rowNum) throws SQLException {

        AnalysisEpCurves analysisEpCurves = new AnalysisEpCurves();
        analysisEpCurves.setAnalysisId(rs.getInt("analysis_id"));
        analysisEpCurves.setFinPerspCode(rs.getString("fin_persp_code"));
        analysisEpCurves.setEpTypeCode(rs.getInt("ep_type_code"));
        analysisEpCurves.setLoss(rs.getDouble("loss"));
        analysisEpCurves.setExeceedanceProb(rs.getBigDecimal("exceedance_probability"));

        return analysisEpCurves;


    }
}
