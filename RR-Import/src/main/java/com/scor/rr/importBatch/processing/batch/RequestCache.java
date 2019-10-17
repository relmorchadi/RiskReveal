package com.scor.rr.importBatch.processing.batch;


import com.scor.rr.domain.entities.cat.CATAnalysis;
import com.scor.rr.domain.entities.cat.CATAnalysisModelResults;
import com.scor.rr.domain.entities.cat.CATObjectGroup;
import com.scor.rr.domain.entities.cat.CATRequest;

public interface RequestCache {
    void queueRequest(String carId, String division, String pb);

    boolean checkRunning(String carId, String division, String pb);

    boolean checkRunningOrQueued(String carId);

    void removeRunning(String carId, String division, String pb);

    CATRequest getRequest(String requestId);

    CATRequest getRequestFromDB(String requestId);

    CATRequest getStatusFromDB(String requestId);

    void persistRequest(String catRequestId);

    void persistRequest(CATRequest catRequest);

    void removeRequest(String requestId);

    CATObjectGroup getCatObjectGroup(String requestId, String division, String periodBasis);

    CATObjectGroup getCatObjectGroupFromDB(String requestId, String division, String periodBasis);

    void persistCatObjectGroup(String requestId, String division, String periodBasis);

    void persistCatObjectGroup(CATObjectGroup catObjectGroup);

    void persistCatAnalysis(CATAnalysis catAnalysis);

    void persistCatAnalysisModelResults(CATAnalysisModelResults catAnalysisModelResults);

    void removeCatObjectGroup(String requestId, String division, String periodBasis);

    void removeCatObjectGroupFromDB(String requestId, String division, String periodBasis);

    void removeCatAnalysisFromDB(String requestId);

    void removeExposureViewsFromDB(String requestId, String division, String periodBasis);
}
