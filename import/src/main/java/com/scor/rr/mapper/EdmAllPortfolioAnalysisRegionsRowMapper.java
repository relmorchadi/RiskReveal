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
        edmAllPortfolioAnalysisRegions.setAnalysisRegion(rs.getString("analysis_region"));
        edmAllPortfolioAnalysisRegions.setAnalysisRegionDesc(rs.getString("analysis_region_desc"));
        edmAllPortfolioAnalysisRegions.setPeril(rs.getString("peril"));
        edmAllPortfolioAnalysisRegions.setTotalTiv(BigDecimal.valueOf(rs.getDouble("total_tiv")).setScale(3, RoundingMode.HALF_UP).doubleValue());
        edmAllPortfolioAnalysisRegions.setLocCount(rs.getInt("loc_count"));

        return edmAllPortfolioAnalysisRegions ;
    }
}
