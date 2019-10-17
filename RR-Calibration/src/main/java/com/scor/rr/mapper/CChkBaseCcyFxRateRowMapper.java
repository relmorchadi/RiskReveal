package com.scor.rr.mapper;

import com.scor.rr.domain.CChkBaseCcyFxRate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CChkBaseCcyFxRateRowMapper implements RowMapper<CChkBaseCcyFxRate> {

    @Override
    public CChkBaseCcyFxRate mapRow(ResultSet rs, int rowNum) throws SQLException {

        CChkBaseCcyFxRate cChkBaseCcyFxRate = new CChkBaseCcyFxRate();

        cChkBaseCcyFxRate.setResult(rs.getInt("result"));
        cChkBaseCcyFxRate.setErrorMessage(rs.getString("error_msg"));

        return cChkBaseCcyFxRate;

    }
}
