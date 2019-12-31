package com.scor.rr.mapper;

import com.scor.rr.domain.riskLink.RLPortfolio;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@NoArgsConstructor
public class PortfolioRowMapper implements RowMapper<RLPortfolio> {


    @Override
    public RLPortfolio mapRow(ResultSet rs, int rowNum) throws SQLException {
        RLPortfolio tmp = new RLPortfolio();
        tmp.setEdmId(rs.getLong("edm_id"));
        tmp.setEdmName(rs.getString("edm_name"));
        tmp.setRlId(rs.getLong("port_id"));
        tmp.setNumber(rs.getString("port_num"));
        tmp.setName(rs.getString("port_name"));
//        tmp.setCreated(translateDate(rs.getTimestamp("port_created_dt")));
        tmp.setDescription(rs.getString("port_descr"));
        tmp.setType(rs.getString("port_type"));
        tmp.setPeril(rs.getString("peril"));
        tmp.setAgSource(rs.getString("ag_source"));
        tmp.setAgCedent(rs.getString("ag_cedant"));
        tmp.setAgCurrency(rs.getString("ag_ccy"));
        tmp.setTiv(rs.getBigDecimal("tiv"));
        return tmp;
    }

    private static Date translateDate(java.sql.Timestamp date) {
        if (date != null)
            return new Date(date.getTime());
        return null;
    }
}
