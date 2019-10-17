package com.scor.rr.service;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.references.cat.mapping.AnalysisRegionMapping;
import com.scor.rr.domain.entities.references.cat.mapping.region.RegionPerilMapping;
import com.scor.rr.domain.enums.RegionPerilCacheKey;
import com.scor.rr.repository.plt.RegionPerilRepository;
import com.scor.rr.repository.references.RegionPerilMappingRepository;
import com.scor.rr.repository.region.TTAnalysisRegionMappingRepository;
import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Region Peril Mapping Cache Service
 *
 * @author HADDINI Zakariyae
 */
@Service
public class RegionPerilMappingCacheService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RegionPerilMappingCacheService.class);

    private LoadingCache<MultiKey, Optional<RegionPeril>> regionPerilCache;
    private LoadingCache<MultiKey, Optional<RegionPerilMapping>> regionPerilMappingCache;
    private LoadingCache<MultiKey, Optional<AnalysisRegionMapping>> analysisRegionMappingCache;
    private LoadingCache<String, Optional<List<RegionPerilMapping>>> regionPerilMappingPerRegionPerilCache;

    @Autowired
    private RegionPerilRepository regionPerilRepository;

    @Autowired
    private RegionPerilMappingRepository regionPerilMappingRepository;

    @Autowired
    private TTAnalysisRegionMappingRepository analysisRegionMappingRepository;


    public RegionPerilMapping findRegionPerilMappingByCountryCodeAdmin1CodePerilCode(String countryCode,
                                                                                     String admin1Code, String perilCode) {
        // @formatter:off
        return regionPerilMappingCache.getUnchecked(new MultiKey(countryCode, admin1Code, perilCode))
                .orNull();
        // @formatter:on
    }

    public RegionPeril findRegionPerilByRegionPerilCode(String regionPerilCode) {
        // @formatter:off
        return regionPerilCache.getUnchecked(new MultiKey(RegionPerilCacheKey.REGIONPERILCODE, regionPerilCode))
                .orNull();
        // @formatter:on
    }

    public RegionPeril findRegionPerilByRegionPerilId(Integer regionPerilID) {
        // @formatter:off
        return regionPerilCache.getUnchecked(new MultiKey(RegionPerilCacheKey.REGIONPERILID, regionPerilID))
                .orNull();
        // @formatter:on
    }

    /**
     * {@inheritDoc}
     */
    public void afterPropertiesSet() throws Exception {
        // @formatter:off
        regionPerilMappingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<MultiKey, Optional<RegionPerilMapping>>() {

                            @Override
                            public Optional<RegionPerilMapping> load(MultiKey key) throws Exception {
                                return Optional
                                        .fromNullable(regionPerilMappingRepository.findByCountryCodeAndAdmin1CodeAndPerilCode(
                                                (String) key.getKey(0),
                                                (String) key.getKey(1),
                                                (String) key.getKey(2)));
                            }

                        });

        regionPerilCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<MultiKey, Optional<RegionPeril>>() {

                            @Override
                            public Optional<RegionPeril> load(MultiKey key) throws Exception {
                                if (RegionPerilCacheKey.REGIONPERILCODE.equals(key.getKeys()[0]))
                                    return Optional.fromNullable(
                                            regionPerilRepository.findByRegionPerilCode((String) key.getKeys()[1]));
                                else if (RegionPerilCacheKey.REGIONPERILID.equals(key.getKeys()[0]))
                                    return Optional.fromNullable(
                                            regionPerilRepository.findById((Long) key.getKeys()[1])
                                                    .orElse(null));

                                return Optional.absent();
                            }

                        });

        regionPerilMappingPerRegionPerilCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<String, Optional<List<RegionPerilMapping>>>() {

                            @Override
                            public Optional<List<RegionPerilMapping>> load(String regionPerilCode) throws Exception {
                                RegionPeril rp = findRegionPerilByRegionPerilCode(regionPerilCode);

                                if (ALMFUtils.isNotNull(rp)) {
                                    logger.warn("region peril '{}' was not found in the references.", regionPerilCode);

                                    return Optional.absent();
                                }

                                return Optional.fromNullable(regionPerilMappingRepository.findByRegionPerilId(rp.getRegionPerilId()));
                            }

                        });

        analysisRegionMappingCache =
                CacheBuilder.newBuilder()
                        .expireAfterWrite(1l, TimeUnit.HOURS)
                        .build(new CacheLoader<MultiKey, Optional<AnalysisRegionMapping>>() {

                            @Override
                            public Optional<AnalysisRegionMapping> load(MultiKey multiKey) throws Exception {
                                return Optional
                                        .fromNullable(analysisRegionMappingRepository.findByAnalysisRegionCodeAndPerilCode(
                                                (String) multiKey.getKey(0),
                                                (String) multiKey.getKey(1)));
                            }

                        });
        // @formatter:on
    }

    public RegionPeril findRegionPerilByCountryCodeAdmin1CodePerilCode(String countryCode, String admin1Code,
                                                                       String perilCode) {
        // @formatter:off
        RegionPeril result = null;

        RegionPerilMapping rpm = findRegionPerilMappingByCountryCodeAdmin1CodePerilCode(countryCode,
                admin1Code,
                perilCode);

        if (ALMFUtils.isNotNull(rpm)) {
            result = findRegionPerilByRegionPerilId(rpm.getRegionPerilId());

            if (!ALMFUtils.isNotNull(result))
                logger.warn("no RegionPeril found for ID#'{}'", new Object[]{rpm.getRegionPerilId()});
        }

        return result;
        // @formatter:on
    }

    public List<RegionPerilMapping> findRegionPerilMappingByRegionPerilCode(String regionPerilCode) {
        // @formatter:off
        List<RegionPerilMapping> results = regionPerilMappingPerRegionPerilCache.getUnchecked(regionPerilCode)
                .orNull();

        if (!ALMFUtils.isNotNull(results)) {
            results = new ArrayList<>();

            logger.warn("empty RegionPerilMapping for RegionPeril '{}'", regionPerilCode);
        } else if (logger.isDebugEnabled())
            logger.trace("RegionPeril '{}' span to RegionPerilMapping(s) {}",
                    new Object[]{regionPerilCode, results});

        return results;
        // @formatter:on
    }

    public RegionPeril findRegionPerilByAnalysisRegionAndPerilCode(String analysisRegion, String peril) {
        // @formatter:off
        logger.trace("findRegionPerilByAnalysisRegionAndPerilCode : {}:{}", new Object[]{analysisRegion, peril});

        AnalysisRegionMapping analysisRegionMapping = analysisRegionMappingCache.getUnchecked(new MultiKey(analysisRegion, peril))
                .orNull();

        if (!ALMFUtils.isNotNull(analysisRegionMapping)) {
            logger.warn("AnalysisRegionMapping not found for '{}':'{}'", new Object[]{analysisRegion, peril});

            return null;
        }

        return findRegionPerilByCountryCodeAdmin1CodePerilCode(analysisRegionMapping.getCountryCode(),
                analysisRegionMapping.getAdmin1Code(),
                analysisRegionMapping.getPerilCode());
        // @formatter:on
    }

    public void invalidateAll() {
        if (ALMFUtils.isNotNull(regionPerilCache))
            regionPerilCache.invalidateAll();

        if (ALMFUtils.isNotNull(regionPerilMappingCache))
            regionPerilMappingCache.invalidateAll();

        if (ALMFUtils.isNotNull(regionPerilMappingPerRegionPerilCache))
            regionPerilMappingPerRegionPerilCache.invalidateAll();

        if (ALMFUtils.isNotNull(analysisRegionMappingCache))
            analysisRegionMappingCache.invalidateAll();
    }

}
