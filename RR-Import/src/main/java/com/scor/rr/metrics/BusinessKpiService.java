package com.scor.rr.metrics;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.scor.rr.domain.enums.MetricType;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Business Kpi Service Interface
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface BusinessKpiService {

	/**
	 * get current dateTime
	 * 
	 * @return
	 */
	public Date getCurrentDateTime();

	/**
	 * get hit value
	 * 
	 * @param aKpiName
	 * @return
	 */
	public Double getHitValue(String aKpiName);

	/**
	 * get hit quantity
	 * 
	 * @param aKpiName
	 * @return
	 */
	public Double getHitQuantity(String aKpiName);

	/**
	 * get end state
	 * 
	 * @param aKpiName
	 * @param correlationId
	 * @return
	 */
	public String getEndState(String aKpiName, String correlationId);

	/**
	 * get states count
	 * 
	 * @param aKpiName
	 * @param correlationId
	 * @return
	 */
	public Integer getStatesCount(String aKpiName, String correlationId);

	/**
	 * get hit quantity before
	 * 
	 * @param aKpiName
	 * @param aDateTime
	 * @return
	 */
	public Double getHitQuantityBefore(String aKpiName, DateTime aDateTime);

	/**
	 * get hit quantity between
	 * 
	 * @param aKpiName
	 * @param anInterval
	 * @return
	 */
	public Double getHitQuantityBetween(String aKpiName, Interval anInterval);

	/**
	 * get hit quantity in last
	 * 
	 * @param aKpiName
	 * @param aDateCodeLetter
	 * @return
	 */
	public Double getHitQuantityInLast(String aKpiName, String aDateCodeLetter);

	/**
	 * get end state with count
	 * 
	 * @param aKpiName
	 * @param aKpiEndStatus
	 * @return
	 */
	public Integer getEndStateWithCount(String aKpiName, String aKpiEndStatus);

	/**
	 * get hit quantity with details
	 * 
	 * @param aKpiName
	 * @param discriminationCode
	 * @param kpiExtraInfoResultList
	 * @return
	 */
	public Double getHitQuantityWithDetails(String aKpiName, Integer discriminationCode,
                                            List<Map<String, Object>> kpiExtraInfoResultList);

	/**
	 * get hit quantity filter
	 *
	 * @param aKpiName
	 * @param extraInfoFilter
	 * @return
	 */
	public Double getHitQuantityFilter(String aKpiName, Map<String, Object> extraInfoFilter);

	/**
	 * get hit quantity with details
	 *
	 * @param aKpiName
	 * @param discriminationCode
	 * @param kpiExtraInfoRequestList
	 * @param kpiExtraInfoResultList
	 * @return
	 */
	public Double getHitQuantityWithDetails(String aKpiName, Integer discriminationCode,
                                            List<String> kpiExtraInfoRequestList, List<Map<String, Object>> kpiExtraInfoResultList);

	/**
	 * get count with end state in last
	 *
	 * @param aKpiName
	 * @param aKpiEndStatus
	 * @param aDateCodeLetter
	 * @return
	 */
	public Integer getCountWithEndStateInLast(String aKpiName, String aKpiEndStatus, String aDateCodeLetter);

	/**
	 * get metric value
	 *
	 * @param aKpiName
	 * @param type
	 * @param additionalMetricInfo
	 * @return
	 */
	@Timed
	@ExceptionMetered
	public Number getMetricValue(String aKpiName, MetricType type, Map<String, Object> additionalMetricInfo);

	/**
	 * get states
	 *
	 * @param aKpiName
	 * @param correlationId
	 * @return
	 */
	public List<?> getStates(String aKpiName, String correlationId);

	/**
	 * get hit occurrences
	 *
	 * @param aKpiName
	 * @return
	 */
	public List<Map<String, Object>> getHitOccurrences(String aKpiName);

	/**
	 * get number of hits aggregated by
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getNbrOfHitsAggregatedBy(String aKpiName,
                                                              String aDateOfAggregationFilterCodeLetter);

	/**
	 * get number of hits aggregated by in last
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @param aDateCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getNbrOfHitsAggregatedByInLast(String aKpiName,
                                                                    String aDateOfAggregationFilterCodeLetter, String aDateCodeLetter);

	/**
	 * get number aggregated by with end state
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @param aKpiEndStatus
	 * @return
	 */
	public List<Map<String, Object>> getNbrAggregatedByWithEndState(String aKpiName,
                                                                    String aDateOfAggregationFilterCodeLetter, String aKpiEndStatus);

	/**
	 * get total value of hits aggregated by
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getTotalValueOfHitsAggregatedBy(String aKpiName,
                                                                     String aDateOfAggregationFilterCodeLetter);

	/**
	 * get average value of hits aggregated by
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getAverageValueOfHitsAggregatedBy(String aKpiName,
                                                                       String aDateOfAggregationFilterCodeLetter);

	/**
	 * get total value of hits aggregated by in last
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @param aDateCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getTotalValueOfHitsAggregatedByInLast(String aKpiName,
                                                                           String aDateOfAggregationFilterCodeLetter, String aDateCodeLetter);

	/**
	 * get average value of hits aggregated by in last
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @param aDateCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getAverageValueOfHitsAggregatedByInLast(String aKpiName,
                                                                             String aDateOfAggregationFilterCodeLetter, String aDateCodeLetter);

	/**
	 * get number aggregated by with end state in last
	 *
	 * @param aKpiName
	 * @param aDateOfAggregationFilterCodeLetter
	 * @param aKpiEndStatus
	 * @param aDateCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getNbrAggregatedByWithEndStateInLast(String aKpiName,
                                                                          String aDateOfAggregationFilterCodeLetter, String aKpiEndStatus, String aDateCodeLetter);

	/**
	 * get hit occurrences in last
	 *
	 * @param aKpiName
	 * @param aDateCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getHitOccurrencesInLast(String aKpiName, String aDateCodeLetter);

	/**
	 * get occurrences with end state
	 *
	 * @param aKpiName
	 * @param aKpiEndStatus
	 * @return
	 */
	public List<Map<String, Object>> getOccurrencesWithEndState(String aKpiName, String aKpiEndStatus);

	/**
	 * get occurrences with end state in last
	 *
	 * @param aKpiName
	 * @param aKpiEndStatus
	 * @param aDateCodeLetter
	 * @return
	 */
	public List<Map<String, Object>> getOccurrencesWithEndStateInLast(String aKpiName, String aKpiEndStatus,
                                                                      String aDateCodeLetter);

	/**
	 * save hit
	 *
	 * @param aKpiName
	 */
	public void saveHit(String aKpiName);

	/**
	 * save hit
	 *
	 * @param aKpiName
	 * @param value
	 */
	public void saveHit(String aKpiName, Double value);

	/**
	 * save start
	 *
	 * @param aKpiName
	 * @param correlationId
	 */
	public void saveStart(String aKpiName, String correlationId);

	/**
	 * save hit
	 *
	 * @param aKpiName
	 * @param additionalMetricInfo
	 */
	public void saveHit(String aKpiName, Map<String, Object> additionalMetricInfo);

	/**
	 * save end
	 *
	 * @param aKpiName
	 * @param correlationId
	 * @param aKpiEndStatus
	 * @param additionalMetricInfo
	 */
	public void saveEnd(String aKpiName, String correlationId, String aKpiEndStatus,
                        Map<String, Object> additionalMetricInfo);

	/**
	 * save end
	 *
	 * @param aKpiName
	 * @param correlationId
	 * @param aKpiEndStatus
	 */
	public void saveEnd(String aKpiName, String correlationId, String aKpiEndStatus);

	/**
	 * save state
	 *
	 * @param aKpiName
	 * @param correlationId
	 * @param aKpiStateStatus
	 * @param additionalMetricInfo
	 */
	public void saveState(String aKpiName, String correlationId, String aKpiStateStatus,
                          Map<String, Object> additionalMetricInfo);

	/**
	 * save state
	 * 
	 * @param aKpiName
	 * @param correlationId
	 * @param aKpiStateStatus
	 */
	public void saveState(String aKpiName, String correlationId, String aKpiStateStatus);

	/**
	 * save hit
	 * 
	 * @param aKpiName
	 * @param value
	 * @param additionalMetricInfo
	 */
	public void saveHit(String aKpiName, Double value, Map<String, Object> additionalMetricInfo);

	/**
	 * save start
	 * 
	 * @param aKpiName
	 * @param correlationId
	 * @param additionalMetricInfo
	 */
	public void saveStart(String aKpiName, String correlationId, Map<String, Object> additionalMetricInfo);

	/**
	 * save duration
	 * 
	 * @param aKpiName
	 * @param start
	 * @param end
	 * @param additionalMetricInfo
	 */
	public void saveDuration(String aKpiName, Double start, Double end, Map<String, Object> additionalMetricInfo);

	/**
	 * save metric
	 * 
	 * @param aKpiName
	 * @param type
	 * @param metricValue
	 * @param metaDataMetric
	 */
	public void saveMetric(String aKpiName, MetricType type, Double metricValue, Map<String, Object> metaDataMetric);

}
