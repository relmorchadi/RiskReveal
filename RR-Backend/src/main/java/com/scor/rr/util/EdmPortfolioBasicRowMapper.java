package com.scor.rr.util;

import com.scor.rr.domain.EdmPortfolioBasic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class EdmPortfolioBasicRowMapper implements RowMapper<EdmPortfolioBasic> {

    @Override
    public EdmPortfolioBasic mapRow(ResultSet rs, int rowNum) throws SQLException {

        EdmPortfolioBasic edmPortfolioBasic = new EdmPortfolioBasic() ;

        edmPortfolioBasic.setEdmId(rs.getLong("edm_id"));
        edmPortfolioBasic.setEdmName(rs.getString("edm_name"));
        edmPortfolioBasic.setPortfolioId(rs.getLong("port_id"));
        edmPortfolioBasic.setNumber(rs.getString("port_num"));
        edmPortfolioBasic.setName(rs.getString("port_name"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        edmPortfolioBasic.setCreated(df.format(rs.getTimestamp("port_created_dt").toLocalDateTime()));
        edmPortfolioBasic.setDescription(rs.getString("port_descr"));
        edmPortfolioBasic.setType(rs.getString("port_type"));
        edmPortfolioBasic.setPeril(rs.getString("peril"));
        edmPortfolioBasic.setAgSource(rs.getString("ag_source"));
        edmPortfolioBasic.setAgCedant(rs.getString("ag_cedant"));
        edmPortfolioBasic.setAgCurrency(rs.getString("ag_ccy"));

        return  edmPortfolioBasic;
    }
}
