package com.scor.rr.importBatch.processing.datasources;

import javax.sql.DataSource;

/**
 * DSCache Interface
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface DSCache {

	/**
	 * get DataSource
	 * 
	 * @param instanceId
	 * @return
	 */
	DataSource getDataSource(String instanceId);

}
