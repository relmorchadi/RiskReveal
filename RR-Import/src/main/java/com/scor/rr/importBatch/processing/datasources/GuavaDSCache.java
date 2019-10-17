package com.scor.rr.importBatch.processing.datasources;

import com.google.common.cache.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.utils.ALMFUtils;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * GuavaDSCache Implementation
 * 
 * @author HADDINI Zakariyae
 *
 */
@Service
@Data
public class GuavaDSCache implements DSCache {

	private static final Logger logger = LoggerFactory.getLogger(GuavaDSCache.class);

	@Value("${rms.ds.initialSize}")
	protected Integer initialSize;
	@Value("${rms.ds.maxActive}")
	protected Integer maxActive;
	@Value("${rms.ds.acquireIncrement}")
	protected Integer acquireIncrement;
	@Value("${rms.ds.maxStatements}")
	protected Integer maxStatements;
	@Value("${rms.ds.idleConnectionTestPeriod}")
	protected Integer idleConnectionTestPeriod;

	private LoadingCache<String, DataSource> cache;

	public GuavaDSCache() {
	}

	public GuavaDSCache(ModellingSystemInstanceRepository modellingSystemInstanceRepository) {
		// @formatter:off
		cache = CacheBuilder.newBuilder()
							.maximumSize(50)
							.expireAfterAccess(120, TimeUnit.MINUTES)
							.removalListener(new RemovalListener<String, DataSource>() {

								public void onRemoval(RemovalNotification<String, DataSource> notification) {
									DataSource dataSource = notification.getValue();
			
									logger.info("cleaning up datasource '{}'", notification.getKey());
			
									if (ALMFUtils.isNotNull(dataSource)) {
										try {
											DataSources.destroy(dataSource);
										} catch (SQLException e) {
											logger.error("ds destroy error for instance : '{}', ds destroy error : {}",
														 notification.getKey(), e);
			
											throw new RuntimeException(e);
										}
									}
								}
					
							})
							.build(new CacheLoader<String, DataSource>() {
				
								public DataSource load(String instanceId) {
									ModellingSystemInstance instance = modellingSystemInstanceRepository.findById(instanceId)
											.orElse(null);
			
									if (ALMFUtils.isNotNull(instance)) {
										try {
											logger.debug("instanciating RMS datasource '{}' => {}",
													new Object[] { instanceId, ToStringBuilder.reflectionToString(instance) });
			
											return constructAComboPooledDataSource(instanceId, instance);
										} catch (Exception e) {
											logger.error("ds init error for RMS datasource : '{}', ds init error : {} ", instanceId,
													e);
			
											throw new RuntimeException("ds init error", e);
										}
									}
			
									logger.error("instance '{}' was not found in repository", instanceId);
			
									throw new RuntimeException(
											"instance '".concat(instanceId).concat("' was not found in repository"));
								}
				
							});
		// @formatter:on
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized DataSource getDataSource(String instanceId) {
		return cache.getUnchecked(instanceId);
	}

	/**
	 * construct a ComboPooledDataSource
	 * 
	 * @param instanceId
	 * @param instance
	 * @return
	 * @throws PropertyVetoException
	 */
	private DataSource constructAComboPooledDataSource(String instanceId, ModellingSystemInstance instance)
			throws PropertyVetoException {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

		comboPooledDataSource.setDataSourceName(instanceId);

		comboPooledDataSource.setDriverClass(instance.getDriverClass());

		comboPooledDataSource.setJdbcUrl(instance.getUrl());

		comboPooledDataSource.setUser(instance.getLogin());

		comboPooledDataSource.setPassword(instance.getPass());

		comboPooledDataSource.setNumHelperThreads(2);

		comboPooledDataSource.setAcquireRetryAttempts(1);

		comboPooledDataSource.setAcquireRetryDelay(1000);

		comboPooledDataSource.setInitialPoolSize(initialSize);

		comboPooledDataSource.setMinPoolSize(initialSize);

		comboPooledDataSource.setAcquireIncrement(acquireIncrement);

		comboPooledDataSource.setMaxPoolSize(maxActive);

		comboPooledDataSource.setMaxStatements(maxStatements);

		comboPooledDataSource.setTestConnectionOnCheckin(true);

		comboPooledDataSource.setTestConnectionOnCheckout(true);

		comboPooledDataSource.setPreferredTestQuery(instance.getTestQuery());

		comboPooledDataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);

		return comboPooledDataSource;
	}

}
