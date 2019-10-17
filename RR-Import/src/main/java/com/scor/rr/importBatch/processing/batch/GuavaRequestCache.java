package com.scor.rr.importBatch.processing.batch;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.scor.rr.domain.entities.cat.*;
import com.scor.rr.repository.cat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service(value = "requestCache")
public class GuavaRequestCache implements RequestCache {

    public static final String GLOBAL_BROKER_PURE = "Global Broker Pure";
    private static final String DEFAULT = "Default";

    private final LoadingCache<String, CATRequest> cache;
    private final LoadingCache<String, CATObjectGroup> groupCache;
    private final Cache<String, String> runningCache;
    private final Cache<String, String> queuedCache;

    @Autowired
    private CATRequestRepository catRequestRepository;
    @Autowired
    private CATObjectGroupRepository catObjectGroupRepository;
    @Autowired
    private CATAnalysisRepository catAnalysisRepository;
    @Autowired
    private CATAnalysisModelResultsRepository catAnalysisModelResultsRepository;
    @Autowired
    private GlobalExposureViewRepository globalExposureViewRepository;

    public GuavaRequestCache(final CATRequestRepository catRequestRepository, final CATObjectGroupRepository catObjectGroupRepository) {
        this.catRequestRepository = catRequestRepository;
        this.catObjectGroupRepository = catObjectGroupRepository;
        cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(120, TimeUnit.MINUTES)
                .build(new CacheLoader<String, CATRequest>() {
                    @Override
                    public CATRequest load(String s) {
                        // Warning : must never return null  !
                        //TODO : Check if correct or not
                        return catRequestRepository.findById(s).orElse(new CATRequest());
                    }
                });

        groupCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(120, TimeUnit.MINUTES)
                .build(new CacheLoader<String, CATObjectGroup>() {
                    @Override
                    public CATObjectGroup load(String s) {
                        // Warning : must never return null  !
                        //TODO : Check if correct or not
                        return catObjectGroupRepository.findById(s).orElse(new CATObjectGroup());
                    }
                });

        runningCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(120, TimeUnit.MINUTES)
                .build();

        queuedCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(120, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public synchronized void queueRequest(String carId, String division, String pb) {
        String key = getKey(carId, division, pb);
        queuedCache.put(key, "queued");
    }

    @Override
    public synchronized boolean checkRunning(String carId, String division, String pb) {
        String key = getKey(carId, division, pb);
        final String running = runningCache.getIfPresent(key);
        if (running != null)
            return true;

        queuedCache.invalidate(key);
        runningCache.put(key, "running");
        return false;
    }

    private String getKey(String carId, String division, String pb) {
        return carId + "_" + division + "_" + pb;
    }

    @Override
    public synchronized boolean checkRunningOrQueued(String carId) {
        String key = carId + "_";
        for (String running : runningCache.asMap().keySet()) {
            if (running.startsWith(key))
                return true;
        }
        for (String running : queuedCache.asMap().keySet()) {
            if (running.startsWith(key))
                return true;
        }

        return false;
    }

    @Override
    public synchronized void removeRunning(String carId, String division, String pb) {
        runningCache.invalidate(getKey(carId, division, pb));
    }

    @Override
    public synchronized CATRequest getRequest(String requestId) {
        return cache.getUnchecked(requestId);
    }

    @Override
    public synchronized CATRequest getRequestFromDB(String requestId) {
        return (CATRequest) catRequestRepository.findById(requestId).orElse(new CATRequest());
    }

    @Override
    public synchronized CATRequest getStatusFromDB(String requestId) {
        return (CATRequest) catRequestRepository.queryForStatus(requestId);
    }

    @Override
    public synchronized void persistRequest(String catRequestId) {
        catRequestRepository.save(cache.getUnchecked(catRequestId));
    }

    @Override
    public synchronized void persistRequest(CATRequest catRequest) {
        catRequestRepository.save(catRequest);
    }

    @Override
    public synchronized void removeRequest(String requestId) {
        cache.invalidate(requestId);
    }

    @Override
    public synchronized CATObjectGroup getCatObjectGroup(String requestId, String division, String periodBasis) {
        return groupCache.getUnchecked(CATObjectGroup.buildId(requestId, division, periodBasis, DEFAULT));
    }

    @Override
    public synchronized CATObjectGroup getCatObjectGroupFromDB(String requestId, String division, String periodBasis) {
        return catObjectGroupRepository.findById(CATObjectGroup.buildId(requestId, division, periodBasis, DEFAULT)).orElse(new CATObjectGroup());
    }

    @Override
    public synchronized void persistCatObjectGroup(String requestId, String division, String periodBasis) {
        catObjectGroupRepository.save(groupCache.getUnchecked(CATObjectGroup.buildId(requestId, division, periodBasis, DEFAULT)));
    }

    @Override
    public synchronized void persistCatObjectGroup(CATObjectGroup catObjectGroup) {
        groupCache.put(catObjectGroup.getCatObjectGroupId(), catObjectGroup);
        catObjectGroupRepository.save(catObjectGroup);
    }

    @Override
    public synchronized void persistCatAnalysis(CATAnalysis catAnalysis) {
        catAnalysisRepository.save(catAnalysis);
    }

    @Override
    public synchronized void persistCatAnalysisModelResults(CATAnalysisModelResults catAnalysisModelResults) {
        catAnalysisModelResultsRepository.save(catAnalysisModelResults);
    }

    @Override
    public synchronized void removeCatObjectGroup(String requestId, String division, String periodBasis) {
        groupCache.invalidate(CATObjectGroup.buildId(requestId, division, periodBasis, DEFAULT));
    }

    @Override
    public void removeCatObjectGroupFromDB(String requestId, String division, String periodBasis) {
        final String key = CATObjectGroup.buildId(requestId, division, periodBasis, DEFAULT);
        groupCache.invalidate(key);
        if (catObjectGroupRepository.findById(key).isPresent())
            catObjectGroupRepository.deleteById(key);
    }

    @Override
    public void removeCatAnalysisFromDB(String requestId) {

        for (CATAnalysis catAnalysis : catAnalysisRepository.findByCATRequestId(requestId)) {
            final String key = catAnalysis.getCatAnalysisId();

            //TODO : Check later if it's correct
            for (CATAnalysisModelResults modelResults : catAnalysisModelResultsRepository.findByCATAnalysisId(key)) {
                if (catAnalysisModelResultsRepository.findById(modelResults.getCATAnalysisModelResultsId()).isPresent())
                    catAnalysisModelResultsRepository.deleteById(modelResults.getCATAnalysisModelResultsId());
            }

            if (catAnalysisRepository.findById(key).isPresent())
                catAnalysisRepository.deleteById(key);

        }

    }

    @Override
    public void removeExposureViewsFromDB(String requestId, String division, String periodBasis) {
        globalExposureViewRepository.deleteById(GlobalExposureView.buildId(requestId, division, periodBasis, GLOBAL_BROKER_PURE));
    }
}
