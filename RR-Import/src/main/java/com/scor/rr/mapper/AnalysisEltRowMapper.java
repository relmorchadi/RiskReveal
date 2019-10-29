package com.scor.rr.mapper;

import com.scor.rr.domain.RlEltLoss;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalysisEltRowMapper implements RowMapper<RlEltLoss> {

    @Override
    public RlEltLoss mapRow(ResultSet rs , int rowNum) throws SQLException {

        RlEltLoss rlEltLoss = new RlEltLoss();

        rlEltLoss.setAnalysisId(rs.getInt("analysis_id"));
        rlEltLoss.setFinPerspCode(rs.getString("fin_persp_code"));
        rlEltLoss.setEventId(rs.getLong("event_id"));
        rlEltLoss.setRate(rs.getDouble("rate"));
        rlEltLoss.setLoss(BigDecimal.valueOf(rs.getDouble("loss")).setScale(7, RoundingMode.HALF_UP).doubleValue());
        rlEltLoss.setStdDevI(rs.getDouble("std_dev_i"));
        rlEltLoss.setStdDevC(BigDecimal.valueOf(rs.getDouble("std_dev_c")).setScale(6,RoundingMode.HALF_UP).doubleValue());
        rlEltLoss.setExposureValue(rs.getDouble("exposure_value"));
        rlEltLoss.setStdDevUSq(rlEltLoss.getStdDevC()+ rlEltLoss.getStdDevI());

        return rlEltLoss;

    }
}
