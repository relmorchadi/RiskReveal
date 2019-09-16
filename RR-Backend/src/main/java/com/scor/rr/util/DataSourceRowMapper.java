package com.scor.rr.util;

import com.scor.rr.domain.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DataSourceRowMapper implements RowMapper<DataSource> {


    @Override
    public DataSource mapRow(ResultSet rs, int rowNum) throws SQLException {

        DataSource dataSource = new DataSource();
        dataSource.setRmsId(rs.getLong("db_id"));
        dataSource.setName(rs.getString("db_name"));
        if (StringUtils.equalsIgnoreCase(rs.getString("db_type"), "EDM")) {
            dataSource.setType(ModelDataSourceType.EDM);
        } else if (StringUtils.equalsIgnoreCase(rs.getString("db_type"), "RDM")) {
            dataSource.setType(ModelDataSourceType.RDM);
        } else {
            dataSource.setType(ModelDataSourceType.Unknown);
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        dataSource.setDateCreated(df.format(rs.getTimestamp("create_dt").toLocalDateTime()));
        dataSource.setVersionId(rs.getInt("version_num"));

        return dataSource;

    }

}
