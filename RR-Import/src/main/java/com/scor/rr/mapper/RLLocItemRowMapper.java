package com.scor.rr.mapper;

import com.scor.rr.service.batch.processor.rows.RLLocRow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RLLocItemRowMapper implements RowMapper<RLLocRow> {

    @Override
    public RLLocRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RLLocRow(
                rs.getString(1),
                rs.getString(2),
                rs.getString(49),
                null,
                null,
                null,
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getInt(6),
                rs.getInt(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12),
                rs.getString(13),
                rs.getString(14),
                rs.getString(15),
                rs.getString(16),
                rs.getString(17),
                rs.getInt(18),
                rs.getString(19),
                rs.getString(20),
                rs.getString(21),
                rs.getString(22),
                rs.getString(23),
                rs.getDouble(24),
                rs.getDouble(25),
                rs.getDouble(26),
                rs.getDouble(27),
                rs.getDouble(28),
                rs.getDouble(29),
                rs.getDouble(30),
                rs.getInt(31),
                rs.getString(32),
                rs.getString(33),
                rs.getString(34),
                rs.getDouble(35),
                rs.getDouble(36),
                rs.getString(38),
                rs.getString(40),
                rs.getString(42),
                rs.getString(44),
                rs.getString(46),
                rs.getString(48));

    }
}
