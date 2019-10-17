package com.scor.rr.importBatch.processing.batch.rms;

import com.scor.rr.importBatch.processing.domain.rms.RMSAccRow;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by U002629 on 03/04/2015.
 */
public class RMSAccRowMapper implements RowMapper<RMSAccRow> {
    @Override
    public RMSAccRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RMSAccRow(
                rs.getString(1),
                rs.getString(2),
                rs.getDate(3),
                rs.getDate(4),
                rs.getString(5),
                rs.getDouble(6),
                rs.getDouble(7));
    }
}
