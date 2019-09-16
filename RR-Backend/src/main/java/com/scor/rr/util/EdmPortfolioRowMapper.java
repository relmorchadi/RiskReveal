package com.scor.rr.util;

import com.scor.rr.domain.EdmPortfolio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class EdmPortfolioRowMapper implements RowMapper<EdmPortfolio> {

    @Override
    public EdmPortfolio mapRow(ResultSet rs , int rowNum ) throws SQLException {

        EdmPortfolio edmPortfolio = new EdmPortfolio();
        edmPortfolio.setEdmId(rs.getLong("edm_id"));
        edmPortfolio.setEdmName(rs.getString("edm_name"));
        edmPortfolio.setPortfolioId(rs.getLong("port_id"));
        edmPortfolio.setNumber(rs.getString("port_num"));
        edmPortfolio.setName(rs.getString("port_name"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        edmPortfolio.setCreated(df.format(rs.getTimestamp("port_created_dt").toLocalDateTime()));
        edmPortfolio.setDescription(rs.getString("port_descr"));
        edmPortfolio.setType(rs.getString("port_type"));
        edmPortfolio.setPeril(rs.getString("peril"));
        edmPortfolio.setAgSource(rs.getString("ag_source"));
        edmPortfolio.setAgCedant(rs.getString("ag_cedant"));
        edmPortfolio.setAgCurrency(rs.getString("ag_ccy"));
        edmPortfolio.setTiv(rs.getBigDecimal("tiv"));

        return  edmPortfolio ;
    }

}
