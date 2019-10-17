package com.scor.rr.utils.mapper;

import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.rms.RmsModelDatasource;
import com.scor.rr.domain.enums.ModelDataSourceType;
import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dm Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class DmRowMapper implements RowMapper<RmsModelDatasource> {

	private String instanceId;
	private Long versionId;

	private ModellingSystemInstance modellingSystemInstance;

	public DmRowMapper(String instanceId, long versionId, ModellingSystemInstance modellingSystemInstance) {
		this.instanceId = instanceId;
		this.versionId = versionId;
		this.modellingSystemInstance = modellingSystemInstance;
	}

	/**
	 * {@inheritDoc}
	 */
	public RmsModelDatasource mapRow(ResultSet rs, int rowNum) throws SQLException {
		RmsModelDatasource rmsModelDatasource = new RmsModelDatasource();

		if (ALMFUtils.isNotNull(rs))
			fillRmsModelDatasourceFields(rs, rmsModelDatasource);

		return rmsModelDatasource;
	}

	/**
	 * fill RmsModelDatasource fields
	 * 
	 * @param rs
	 * @param rmsModelDatasource
	 * @throws SQLException
	 */
	private void fillRmsModelDatasourceFields(ResultSet rs, RmsModelDatasource rmsModelDatasource) throws SQLException {
		// @formatter:off
		rmsModelDatasource.setRmsId(rs.getLong("db_id"));
		
		rmsModelDatasource.setInstanceId(instanceId);
		
		rmsModelDatasource.setInstanceName(modellingSystemInstance.getName());
		
		rmsModelDatasource.setName(rs.getString("db_name"));
		
		if (ALMFUtils.isNotNull(modellingSystemInstance)
				&& ALMFUtils.isNotNull(modellingSystemInstance.getModellingSystemVersion())
				&& ALMFUtils.isNotNull(modellingSystemInstance.getModellingSystemVersion().getModellingSystem())
				&& ALMFUtils.isNotNull(
						modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor()))
			rmsModelDatasource.setSource(modellingSystemInstance.getModellingSystemVersion()
																.getModellingSystem()
																.getVendor()
																.getName()
																.concat(" ")
																.concat(modellingSystemInstance.getModellingSystemVersion()
																							   .getModellingSystem()
																							   .getName())
																.concat(" ")
																.concat(String.valueOf(modellingSystemInstance.getModellingSystemVersion()
																											  .getModellingSystemVersion())));
		
		if (StringUtils.equalsIgnoreCase(rs.getString("db_type"), "EDM"))
			rmsModelDatasource.setType(ModelDataSourceType.EDM);
		else if (StringUtils.equalsIgnoreCase(rs.getString("db_type"), "RDM"))
			rmsModelDatasource.setType(ModelDataSourceType.RDM);
		else
			rmsModelDatasource.setType(ModelDataSourceType.UNKNOWN);

		rmsModelDatasource.setVersionId(versionId);
		
		rmsModelDatasource.setDateCreated(rs.getDate("create_dt"));
		// @formatter:on
	}
}