package com.scor.rr.util;

import com.scor.rr.domain.RdmAllAnalysisProfileRegions;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RdmAllAnalysisProfileRegionsRowMapper implements RowMapper<RdmAllAnalysisProfileRegions> {

    @Override
    public RdmAllAnalysisProfileRegions mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAllAnalysisProfileRegions rdmAllAnalysisProfileRegions = new RdmAllAnalysisProfileRegions();
        rdmAllAnalysisProfileRegions.setAnalysisid(rs.getLong("analysis_id"));
        rdmAllAnalysisProfileRegions.setAnlysisRegion(rs.getString("analysis_region"));
        rdmAllAnalysisProfileRegions.setAnalysisRegionName(rs.getString("analysis_region_name"));
        rdmAllAnalysisProfileRegions.setProfileRegion(rs.getString("profile_region"));
        rdmAllAnalysisProfileRegions.setProfileRegionName(rs.getString("profile_region_name"));
        rdmAllAnalysisProfileRegions.setPeril(rs.getString("peril"));
        rdmAllAnalysisProfileRegions.setAal(rs.getBigDecimal("aal"));

        return rdmAllAnalysisProfileRegions;
    }
}
