package com.scor.rr.repository;

import com.scor.rr.domain.ExposureSummaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ExposureSummaryDataRepository extends JpaRepository<ExposureSummaryData, Long> {

    @Query(value = "EXEC dbo.usp_ExposureManagerData " +
            "@projectId=:projectId," +
            "@portfolioName=:portfolioName," +
            "@division=:division," +
            "@summaryTitle=:summaryType," +
            "@currency=:currency," +
            "@fp=:fp," +
            "@pageNumber=:pageNumber," +
            "@pageSize=:pageSize," +
            "@paginate=:paginate," +
            "@RegionPerilCodeFilter=:regionPerilCodeFilter," +
            "@Type=:dataType", nativeQuery = true)
    List<Map<String, Object>> getExposureData(Long projectId, String portfolioName, String summaryType, Integer division,
                                              String currency, String fp, Integer pageNumber, Integer pageSize, String regionPerilCodeFilter, Boolean paginate, String dataType);

    @Query(value = "EXEC dbo.usp_ExposureManagerTotalRow " +
            "@projectId=:projectId," +
            "@portfolioName=:portfolioName," +
            "@division=:division," +
            "@summaryTitle=:summaryType," +
            "@currency=:currency," +
            "@fp=:fp," +
            "@Type=:dataType", nativeQuery = true)
    Map<String, Object> getTotalRowExposureData(Long projectId, String portfolioName, String summaryType, Integer division,
                                              String currency, String fp, String dataType);
}
