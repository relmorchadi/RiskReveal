package com.scor.rr.util;

import com.scor.rr.domain.RmsExchangeRate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class RmsExchangeRateRowMapper implements RowMapper<RmsExchangeRate> {

    @Override
    public RmsExchangeRate mapRow(ResultSet rs, int rowNum) throws SQLException {

        RmsExchangeRate rmsExchangeRate = new RmsExchangeRate();
        rmsExchangeRate.setCcy(rs.getString("Ccy"));
        rmsExchangeRate.setRoe(rs.getDouble("Roe"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        rmsExchangeRate.setRoeAsAt(df.format(rs.getTimestamp("RoeAsAt").toLocalDateTime()));

        return rmsExchangeRate;
    }
}
