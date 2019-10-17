package com.scor.rr.mapper;

import com.scor.rr.domain.CChkBaseCcy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CChkBaseCcyRowMApper implements RowMapper<CChkBaseCcy> {
    @Override
    public CChkBaseCcy mapRow(ResultSet rs, int rowNum) throws SQLException {

        CChkBaseCcy cChkBaseCcy = new CChkBaseCcy();

        cChkBaseCcy.setResult(rs.getInt("result"));
        cChkBaseCcy.setErrorMessage(rs.getString("error_msg"));
        
        return cChkBaseCcy;

    }
}
