package com.scor.rr.mapper;

import com.scor.rr.domain.EdmAllPortfolioAnalysisRegions;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdmAllPortfolioAnalysisRegionsRowMapper implements RowMapper<EdmAllPortfolioAnalysisRegions> {

    public EdmAllPortfolioAnalysisRegions mapRow(ResultSet rs , int rowNum) throws SQLException {

        EdmAllPortfolioAnalysisRegions edmAllPortfolioAnalysisRegions = new EdmAllPortfolioAnalysisRegions() ;

        edmAllPortfolioAnalysisRegions.setPortfolioId(rs.getLong("port_id"));
        edmAllPortfolioAnalysisRegions.setPortfolioType(rs.getString("port_type"));
        edmAllPortfolioAnalysisRegions.setAnalysisRegion(rs.getString("AnalysisRegionCode"));
        edmAllPortfolioAnalysisRegions.setAnalysisRegionDesc(rs.getString("AnalysisRegionDesc"));
        edmAllPortfolioAnalysisRegions.setPeril(rs.getString("Peril"));
        edmAllPortfolioAnalysisRegions.setTotalTiv(BigDecimal.valueOf(rs.getDouble("TotalTiv")).setScale(3, RoundingMode.HALF_UP).doubleValue());
        edmAllPortfolioAnalysisRegions.setLocCount(rs.getInt("LocCount"));
        edmAllPortfolioAnalysisRegions.setExpoCurrency(rs.getString("expo_ccy"));
        edmAllPortfolioAnalysisRegions.setRateToUsd(rs.getDouble("expo_ccy_roe_usd"));

        return edmAllPortfolioAnalysisRegions ;
    }
}
