package com.scor.rr.service;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummaryConformerReference;
import com.scor.rr.domain.entities.rms.exposuresummary.ExposureSummaryDefinition;
import com.scor.rr.domain.entities.rms.exposuresummary.SystemExposureSummaryDefinition;
import com.scor.rr.repository.rms.exposuresummary.ExposureSummaryConformerReferenceRepository;
import com.scor.rr.repository.rms.exposuresummary.ExposureSummaryDefinitionRepository;
import com.scor.rr.repository.rms.exposuresummary.SystemExposureSummaryDefinitionRepository;
import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Exposure Summary Reference Cache Service
 *
 * @author HADDINI Zakariyae
 */
@Service
public class ExposureSummaryReferenceCacheService /*implements InitializingBean*/ {

    private LoadingCache<String, Optional<ExposureSummaryDefinition>> exposureSummaryDefinitionCache;
    private LoadingCache<MultiKey, Optional<SystemExposureSummaryDefinition>> systemExposureSummaryDefinitionCache;
    private LoadingCache<MultiKey, Optional<ExposureSummaryConformerReference>> exposureSummaryConformerReferenceCache;

    @Autowired
    private ExposureSummaryDefinitionRepository exposureSummaryDefinitionRepository;

    @Autowired
    private SystemExposureSummaryDefinitionRepository systemExposureSummaryDefinitionRepository;

    @Autowired
    private ExposureSummaryConformerReferenceRepository exposureSummaryConformerReferenceRepository;
    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        // @formatter:off
        exposureSummaryConformerReferenceCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<MultiKey, Optional<ExposureSummaryConformerReference>>() {

                            @Override
                            public Optional<ExposureSummaryConformerReference> load(MultiKey key) throws Exception {
                                return Optional.fromNullable(
                                        exposureSummaryConformerReferenceRepository
                                                .findBySourceVendorAndSourceSystemAndVersionAndAxisConformerAliasAndInputCode(
                                                        (String) key.getKey(0),
                                                        (String) key.getKey(1),
                                                        (String) key.getKey(2),
                                                        (String) key.getKey(3),
                                                        (String) key.getKey(4)));
                            }

                        });

        systemExposureSummaryDefinitionCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<MultiKey, Optional<SystemExposureSummaryDefinition>>() {

                            @Override
                            public Optional<SystemExposureSummaryDefinition> load(MultiKey key) throws Exception {
                                return Optional.fromNullable(
                                        systemExposureSummaryDefinitionRepository
                                                .findBySourceVendorAndSourceSystemAndVersionAndExposureSummaryAlias(
                                                        (String) key.getKey(0),
                                                        (String) key.getKey(1),
                                                        (String) key.getKey(2),
                                                        (String) key.getKey(3)));
                            }

                        });

        exposureSummaryDefinitionCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<String, Optional<ExposureSummaryDefinition>>() {

                            @Override
                            public Optional<ExposureSummaryDefinition> load(String key) throws Exception {
                                return Optional.fromNullable(
                                        exposureSummaryDefinitionRepository.findByExposureSummaryAlias(key));
                            }

                        });
        // @formatter:on
    }

    public ExposureSummaryConformerReference getConformer(String sourceVendor, String sourceSystem, String version,
                                                          String axisConformerAlias, String inputCode) {
        // @formatter:off
        return exposureSummaryConformerReferenceCache.getUnchecked(new MultiKey(sourceVendor,
                sourceSystem,
                version,
                axisConformerAlias,
                inputCode))
                .orNull();
        // @formatter:on
    }

    public SystemExposureSummaryDefinition getSystemExposureSummary(String sourceVendor, String sourceSystem,
                                                                    String version, String summaryAlias) {
        // @formatter:off
        return systemExposureSummaryDefinitionCache.getUnchecked(new MultiKey(sourceVendor,
                sourceSystem,
                version,
                summaryAlias))
                .orNull();
        // @formatter:on
    }

    public ExposureSummaryDefinition getExposureSummaryDefinition(String summaryAlias) {
        // @formatter:off
        return exposureSummaryDefinitionCache.getUnchecked(summaryAlias)
                .orNull();
        // @formatter:on
    }

    public List<ExposureSummaryDefinition> getAllExposureSummaryDefinition() {
        return exposureSummaryDefinitionRepository.findAll();
    }

    public void invalidateAll() {
        if (ALMFUtils.isNotNull(exposureSummaryConformerReferenceCache)) {
            exposureSummaryConformerReferenceCache.invalidateAll();
        }
        if (ALMFUtils.isNotNull(systemExposureSummaryDefinitionCache)) {
            systemExposureSummaryDefinitionCache.invalidateAll();
        }
        if (ALMFUtils.isNotNull(exposureSummaryDefinitionCache)) {
            exposureSummaryDefinitionCache.invalidateAll();
        }
    }

}
