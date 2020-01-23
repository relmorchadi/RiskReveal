package com.scor.rr.mapper;

import com.scor.rr.service.batch.processor.rows.RLAccRow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RLAccItemRowMapper implements RowMapper<RLAccRow> {
    @Override
    public RLAccRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RLAccRow(
                rs.getString(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4),
                rs.getString(5),
                rs.getDouble(6),
                rs.getDouble(7));
    }
}

