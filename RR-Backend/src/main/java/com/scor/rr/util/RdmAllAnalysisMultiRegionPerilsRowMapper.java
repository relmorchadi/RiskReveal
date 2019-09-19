package com.scor.rr.util;

import com.scor.rr.domain.RdmAllAnalysisMultiRegionPerils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RdmAllAnalysisMultiRegionPerilsRowMapper implements RowMapper<RdmAllAnalysisMultiRegionPerils> {

    @Override
    public RdmAllAnalysisMultiRegionPerils mapRow(ResultSet rs, int rowNum) throws SQLException {

        RdmAllAnalysisMultiRegionPerils rdmAllAnalysisMultiRegionPerils = new RdmAllAnalysisMultiRegionPerils();

        rdmAllAnalysisMultiRegionPerils.setRdmId(rs.getLong("rdm_id"));
        rdmAllAnalysisMultiRegionPerils.setRdmName(rs.getString("rdm_name"));
        rdmAllAnalysisMultiRegionPerils.setAnalysisId(rs.getLong("analysis_id"));
        rdmAllAnalysisMultiRegionPerils.setSsRegion(rs.getString("ss_region"));
        rdmAllAnalysisMultiRegionPerils.setSsPeril(rs.getString("ss_peril"));
        rdmAllAnalysisMultiRegionPerils.setSsRegionPeril(rs.getString("ss_region_peril"));
        rdmAllAnalysisMultiRegionPerils.setProfileKey(rs.getString("profile_key"));
        rdmAllAnalysisMultiRegionPerils.setEvCount(rs.getLong("ev_count"));

        return rdmAllAnalysisMultiRegionPerils;
    }
}
