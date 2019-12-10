package com.scor.rr.mapper;

import com.scor.rr.domain.RdmAnalysisEpCurves;
import org.springframework.jdbc.core.RowMapper;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RdmAnalysisEpCurvesRowMapper implements RowMapper<RdmAnalysisEpCurves> {

    public RdmAnalysisEpCurves mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAnalysisEpCurves rdmAnalysisEpCurves = new RdmAnalysisEpCurves();

        rdmAnalysisEpCurves.setAnalysisId(rs.getLong("analysis_id"));
        rdmAnalysisEpCurves.setFinPerspCode(rs.getString("fin_persp_code"));
        rdmAnalysisEpCurves.setTreatyLabelId(rs.getString("treaty_label_id"));
        rdmAnalysisEpCurves.setTreatyLabel(rs.getString("treaty_label"));
        rdmAnalysisEpCurves.setEbpTypeCode(rs.getInt("ep_type_code"));
        rdmAnalysisEpCurves.setLoss(rs.getDouble("loss"));
        rdmAnalysisEpCurves.setExceedanceProbabilty(rs.getInt("exceedance_probability"));
        rdmAnalysisEpCurves.setReturnId(rs.getBigDecimal("return_period").setScale(2, RoundingMode.HALF_UP));

        return rdmAnalysisEpCurves;

    }

}
