package com.scor.rr.configuration;

import com.google.common.cache.*;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.scor.rr.repository.ModellingSystemInstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RmsInstanceCache {


    private LoadingCache<String, DataSource> cache;

    @Value("${ds.cache.params.duration}")
    private int cacheDuration;

    @Value("${ds.cache.params.size}")
    private int cacheSize;

    @Value("${ds.cache.params.initialSize}")
    private int initialSize;

    @Value("${ds.cache.params.maxActive}")
    private int maxActive;

    @Value("${ds.cache.params.acquireIncrement}")
    private int acquireIncrement;

    @Value("${ds.cache.params.maxStatements}")
    private int maxStatements;

    @Value("${ds.cache.params.idleConnectionTestPeriod}")
    private int idleConnectionTestPeriod;

    @Value("${ds.cache.params.retryAttempts}")
    private int retryAttempts;

    @Value("${ds.cache.params.retryDelay}")
    private int retryDelay;

    @Value("${ds.cache.params.helperThreads}")
    private int helperThreads;

    @Autowired
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;

    public synchronized DataSource getDataSource(String instanceId){
        log.debug("getDatasource '{}' ",instanceId);
        return cache.getUnchecked(instanceId);
    }

    @PostConstruct
    private void loadInstances() {
        log.info("Loading RMS instances into cache");
        cache = CacheBuilder.newBuilder().maximumSize(cacheSize).expireAfterAccess(cacheDuration, TimeUnit.MINUTES)
                .removalListener(removalListener)
                .build(cacheLoader);
    }

    private RemovalListener<String, DataSource> removalListener = notification -> {
        final DataSource dataSource = notification.getValue();
        log.info("cleaning up datasource '{}'", notification.getKey());
        if (dataSource != null) {
            try {
                DataSources.destroy(dataSource);
            } catch (SQLException e) {
                log.error("ds destroy error for instance '{}'", notification.getKey());
                log.error("ds destroy error", e);
                throw new RuntimeException(e);
            }
        }
    };

    private CacheLoader<String, DataSource> cacheLoader = new CacheLoader<String, DataSource>() {
        @Override
        public DataSource load(String instanceId) throws Exception {
            return modellingSystemInstanceRepository.findById(instanceId)
                    .map(instance -> {
                        try {
                            log.debug("instanciating RMS datasource '{}' => {}", new Object[]{instanceId, ToStringBuilder.reflectionToString(instance)});
                            ComboPooledDataSource cpds = new ComboPooledDataSource();
                            cpds.setDataSourceName(instanceId);
                            cpds.setDriverClass(instance.getDriverClass());
                            cpds.setJdbcUrl(instance.getUrl());
                            cpds.setUser(instance.getLogin());
                            cpds.setPassword(instance.getPass());
                            // login timeout to avoid getting stuck when either the facing database is unavailable or the pool is exhausted
                            // NOTA maybe externalize parameters
                            cpds.setNumHelperThreads(helperThreads);
                            cpds.setAcquireRetryAttempts(retryAttempts);
                            cpds.setAcquireRetryDelay(retryDelay);
                            cpds.setInitialPoolSize(initialSize);
                            cpds.setMinPoolSize(initialSize);
                            cpds.setAcquireIncrement(acquireIncrement);
                            cpds.setMaxPoolSize(maxActive);
                            cpds.setMaxStatements(maxStatements);
                            cpds.setTestConnectionOnCheckin(true);
                            cpds.setTestConnectionOnCheckout(true);
                            cpds.setPreferredTestQuery(instance.getTestQuery());
                            cpds.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
                            return cpds;
                        } catch (Exception e) {
                            log.error("ds init error for RMS datasource '{}'", instanceId);
                            log.error("ds init error", e);
                            throw new RuntimeException("ds init error", e);
                        }
                    })
                    .orElseThrow(() -> {
//                        log.error("instance '{}' was not found in repository", instanceId);
                        return new RuntimeException("instance '" + instanceId + "' was not found in repository");
                    });
        }
    };


}
