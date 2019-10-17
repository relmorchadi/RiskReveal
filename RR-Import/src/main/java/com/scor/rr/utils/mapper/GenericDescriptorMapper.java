package com.scor.rr.utils.mapper;

import com.scor.rr.utils.ALMFUtils;
import com.scor.rr.utils.GenericDescriptor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generic Descriptor Row Mapper
 * 
 * @author HADDINI Zakariyae
 *
 */
public class GenericDescriptorMapper implements RowMapper<GenericDescriptor> {

	/**
	 * {@inheritDoc}
	 */
	public GenericDescriptor mapRow(ResultSet rs, int arg1) throws SQLException {
		GenericDescriptor genericDescriptor = new GenericDescriptor();

		if (ALMFUtils.isNotNull(rs))
			fillGenericDescriptorFields(rs, genericDescriptor);

		return genericDescriptor;
	}

	/**
	 * fill GenericDescriptor fields
	 * 
	 * @param rs
	 * @param genericDescriptor
	 * @throws SQLException
	 */
	private void fillGenericDescriptorFields(ResultSet rs, GenericDescriptor genericDescriptor) throws SQLException {
		genericDescriptor.setColName(rs.getString("SourceColumnName"));

		genericDescriptor.setDataType(rs.getString("SourceColDataType"));

		genericDescriptor.setTargetName(rs.getString("OutputColName"));

		genericDescriptor.setTargetOrder(rs.getInt("OutputColOrdinalPos"));

		genericDescriptor.setTargetFormat(rs.getString("OutputColStringFormat"));
	}
}