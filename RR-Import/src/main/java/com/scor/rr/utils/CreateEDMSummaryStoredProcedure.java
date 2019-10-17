package com.scor.rr.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Types;

/**
 * Create EDM Summary Stored Procedure
 * 
 * @author HADDINI Zakariyae
 *
 */
public class CreateEDMSummaryStoredProcedure extends StoredProcedure {

	private String name;

	public CreateEDMSummaryStoredProcedure(JdbcTemplate jdbcTemplate, String name) {
		// @formatter:off
		super(jdbcTemplate, name);
		
		this.name = name;

		setFunction(false);
		
		SqlParameter edmID 				= new SqlParameter("Edm_ID", Types.INTEGER);
		
		SqlParameter edmName 			= new SqlParameter("Edm_Name", Types.VARCHAR);
		
		SqlParameter edmPortList 		= new SqlParameter("PortfolioList", Types.VARCHAR);
		
		SqlParameter regionPerilExclude = new SqlParameter("RegionPerilExclude", Types.VARCHAR);
		
		SqlOutParameter runID 			= new SqlOutParameter("RunID", Types.INTEGER);
		
		SqlParameter[] paramArray 		= { edmID, edmName, edmPortList, regionPerilExclude, runID };
		
		super.setParameters(paramArray);
		
		super.compile();
		// @formatter:on
	}

	public String getName() {
		return name;
	}

}