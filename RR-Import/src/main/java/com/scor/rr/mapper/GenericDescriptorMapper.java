package com.scor.rr.mapper;

import com.scor.rr.service.GenericDescriptor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericDescriptorMapper implements RowMapper<GenericDescriptor> {

    public GenericDescriptor mapRow(ResultSet rs, int arg1) throws SQLException {
        String colName = rs.getString("SourceColumnName");
        String dataType = rs.getString("SourceColDataType");
        String targetName = rs.getString("OutputColName");
        int targetOrder = rs.getInt("OutputColOrdinalPos");
        String targetFormat = rs.getString("OutputColStringFormat");
        return new GenericDescriptor(colName, dataType, targetName, targetOrder, targetFormat);
    }
}
