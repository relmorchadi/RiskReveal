package com.scor.rr.mapper;

import com.scor.rr.domain.DataSource;
import com.scor.rr.domain.enums.ModelDataSourceType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class DataSourceRowMapper implements RowMapper<DataSource> {

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public DataSource mapRow(ResultSet rs, int rowNum) throws SQLException {

        DataSource dataSource = new DataSource();
        dataSource.setRmsId(rs.getLong("db_id"));
        dataSource.setName(rs.getString("db_name"));
        if (StringUtils.equalsIgnoreCase(rs.getString("db_type"), "EDM")) {
            dataSource.setType(ModelDataSourceType.EDM.toString());
        } else if (StringUtils.equalsIgnoreCase(rs.getString("db_type"), "RDM")) {
            dataSource.setType(ModelDataSourceType.RDM.toString());
        } else {
            dataSource.setType(ModelDataSourceType.UNKNOWN.toString());
        }

        dataSource.setDateCreated(this.df.format(rs.getTimestamp("create_dt").toLocalDateTime()));
        dataSource.setVersionId(rs.getInt("version_num"));

        dataSource.setMatchedRmsId(rs.getLong("matched_db_id"));
        dataSource.setMatchedName(rs.getString("matched_db_name"));
        dataSource.setMatchedType(rs.getString("matched_db_type"));

        return dataSource;

    }

}
