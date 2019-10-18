package com.scor.rr.mapper;

import com.scor.rr.domain.AnalysisElt;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalysisEltRowMapper implements RowMapper<AnalysisElt> {

    @Override
    public AnalysisElt mapRow(ResultSet rs , int rowNum) throws SQLException {

        AnalysisElt analysisElt = new AnalysisElt();

        analysisElt.setAnalysisId(rs.getInt("analysis_id"));
        analysisElt.setFinPerspCode(rs.getString("fin_persp_code"));
        analysisElt.setEventId(rs.getLong("event_id"));
        analysisElt.setRate(rs.getDouble("rate"));
        analysisElt.setLoss(BigDecimal.valueOf(rs.getDouble("loss")).setScale(7, RoundingMode.HALF_UP).doubleValue());
        analysisElt.setStdDevI(rs.getDouble("std_dev_i"));
        analysisElt.setStdDevC(BigDecimal.valueOf(rs.getDouble("std_dev_c")).setScale(6,RoundingMode.HALF_UP).doubleValue());
        analysisElt.setExposureValue(rs.getDouble("exposure_value"));

        return  analysisElt;

    }
}
