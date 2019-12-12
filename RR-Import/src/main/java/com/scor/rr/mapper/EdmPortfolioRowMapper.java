package com.scor.rr.mapper;

import com.scor.rr.domain.EdmPortfolio;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class EdmPortfolioRowMapper implements RowMapper<EdmPortfolio> {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public EdmPortfolio mapRow(ResultSet rs, int rowNum) throws SQLException {

        EdmPortfolio edmPortfolio = new EdmPortfolio();
        edmPortfolio.setEdmId(rs.getLong("edm_id"));
        edmPortfolio.setEdmName(rs.getString("edm_name"));
        edmPortfolio.setPortfolioId(rs.getLong("port_id"));
        edmPortfolio.setNumber(rs.getString("port_num"));
        edmPortfolio.setName(rs.getString("port_name"));
        edmPortfolio.setDescription(rs.getString("port_descr"));
        edmPortfolio.setType(rs.getString("port_type"));
        edmPortfolio.setPeril(rs.getString("peril"));
        edmPortfolio.setAgSource(rs.getString("ag_source"));
        edmPortfolio.setAgCedant(rs.getString("ag_cedant"));
        edmPortfolio.setAgCurrency(rs.getString("ag_ccy"));
        edmPortfolio.setTiv(rs.getBigDecimal("tiv"));
        try {
            edmPortfolio.setCreated(df.parse(rs.getString("port_created_dt")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return edmPortfolio;
    }

}
