package com.scor.rr.metrics;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scor.rr.domain.enums.MetricType;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Business Kpi Service Implementation
 * 
 * @author HADDINI Zakariyae
 *
 */
@Service(value = "businessKpiService")
public class BusinessKpiServiceImpl implements BusinessKpiService {

	private static Logger logger = LoggerFactory.getLogger(BusinessKpiServiceImpl.class);

	private static final String ES_TYPE_NAME_FOR_KPI = "kpi";

	@Value("${datalake.metric.index}")
	private String indexName;
	@Value("${spring.application.name}")
	private String applicationName;
	@Value("${datalake.metric.indexDateFormat}")
	private String indexDateFormat;
	@Value("${datalake.metric.env}")
	private String targetEnvironmentName;

	private transient Boolean indexExists;
	private transient String lastIndexName;

	@Autowired
	private JestClient jestClient;

	public BusinessKpiServiceImpl() {
		// @formatter:off
		indexExists 			= false;
		// @formatter:on
	}

	public BusinessKpiServiceImpl(String anApplicationName, String anEnvironmentName) {
		this();

		try {
			this.applicationName = anApplicationName;
			this.targetEnvironmentName = anEnvironmentName;
		} catch (Exception e) {
			logger.error("error during instatiating a businessKpiService - Exception: {}", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date getCurrentDateTime() {
		return LocalDateTime.now().toDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitValue(String aKpiName) {
		return getValueOfMetric(aKpiName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantity(String aKpiName) {
		return getQuantitySearchValue(aKpiName, null, null).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getEndState(String aKpiName, String correlationId) {
		return "NoImplemented";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getStatesCount(String aKpiName, String correlationId) {
		return getQuantitySearchValueWithCorrelationId(applicationName, targetEnvironmentName, aKpiName, correlationId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantityBefore(String aKpiName, DateTime aDateTime) {
		return 0d; // Not implemented yet
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantityBetween(String aKpiName, Interval anInterval) {
		return 0d; // Not implemented yet
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantityInLast(String aKpiName, String aDateCodeLetter) {
		if ((aDateCodeLetter != "y") && (aDateCodeLetter != "M") && (aDateCodeLetter != "w") && (aDateCodeLetter != "d")
				&& (aDateCodeLetter != "h") && (aDateCodeLetter != "m") && (aDateCodeLetter != "s")
				&& (aDateCodeLetter != null) && (aDateCodeLetter != "0"))
			return -1d;

		return (aDateCodeLetter == null) || (aDateCodeLetter == "0")
				? getQuantitySearchValue(aKpiName, null, null).doubleValue()
				: getQuantitySearchValue(aKpiName, null, aDateCodeLetter).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getEndStateWithCount(String aKpiName, String aKpiEndStatus) {
		return getQuantitySearchValueWithEndState(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT,
				aKpiEndStatus);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantityWithDetails(String aKpiName, Integer discriminationCode,
			List<Map<String, Object>> kpiExtraInfoResultList) {
		return getQuantitySearchValueWithDetails(aKpiName, null, kpiExtraInfoResultList).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantityFilter(String aKpiName, Map<String, Object> extraInfoFilter) {
		return getQuantitySearchValue(aKpiName, null, null).doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getHitQuantityWithDetails(String aKpiName, Integer discriminationCode,
			List<String> kpiExtraInfoRequestList, List<Map<String, Object>> kpiExtraInfoResultList) {
		return getQuantitySearchValueWithDetails(aKpiName, kpiExtraInfoRequestList, kpiExtraInfoResultList)
				.doubleValue();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getCountWithEndStateInLast(String aKpiName, String aKpiEndStatus, String aDateCodeLetter) {
		// @formatter:off
		return "0".equals(aDateCodeLetter)
				? getEndStateWithCount(aKpiName, aKpiEndStatus)
				: getQuantitySearchValueWithEndState(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT,
						aKpiEndStatus);
		// @formatter:on
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Number getMetricValue(String aKpiName, MetricType type, Map<String, Object> additionalMetricInfo) {
		logger.debug("getMetricValue(...) start - {}, {}, {}", aKpiName, type, additionalMetricInfo);

		Number resultat = getMetricValue(aKpiName);

		logger.debug("getMetricValue(...) resultat : {}", resultat);

		logger.debug("getMetricValue(...) end - {}, {}, {}", aKpiName, type, additionalMetricInfo);

		return resultat;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<?> getStates(String aKpiName, String correlationId) {
		return new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> getHitOccurrences(String aKpiName) {
		return getOccurrencesSearchValue(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT, "0");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNbrOfHitsAggregatedBy(String aKpiName,
			String aDateOfAggregationFilterCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_F(aKpiName.toString(),
					aDateOfAggregationFilterCodeLetter, MetricStatics.JSON_DATA_05_HIT_KPI_AGGREG_VALUES_BY);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					Integer numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error("error during getting number of hits aggregated by - Exception: {}", e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed ! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNbrOfHitsAggregatedByInLast(String aKpiName,
			String aDateOfAggregationFilterCodeLetter, String aDateCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null))
			return null;

		if ((aDateCodeLetter != "y") && (aDateCodeLetter != "M") && (aDateCodeLetter != "w") && (aDateCodeLetter != "d")
				&& (aDateCodeLetter != "h") && (aDateCodeLetter != "m") && (aDateCodeLetter != "s")
				&& (aDateCodeLetter != null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_G(aKpiName.toString(),
					aDateOfAggregationFilterCodeLetter, aDateCodeLetter,
					MetricStatics.JSON_DATA_06_HIT_KPI_AGGREG_VALUES_BY_AND_RETROACTIV);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					Integer numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							HashMap<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error("error during getting number of hits aggregated by in last - Exception: {}",
									e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNbrAggregatedByWithEndState(String aKpiName,
			String aDateOfAggregationFilterCodeLetter, String aKpiEndStatus) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null) || (aKpiEndStatus == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_E(aKpiName.toString(), aKpiEndStatus,
					aDateOfAggregationFilterCodeLetter, MetricStatics.JSON_DATA_03_KPI_AGGREG_VALUES_WITH_AND_STATUS);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					int numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error("error during getting number of aggregated by with end state - Exception: {}",
									e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTotalValueOfHitsAggregatedBy(String aKpiName,
			String aDateOfAggregationFilterCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_F(aKpiName.toString(),
					aDateOfAggregationFilterCodeLetter, MetricStatics.JSON_DATA_07_VALUE_KPI_AGGREG_BY_TOTAL_VALUE);

			SearchResult aSearchResult = this.executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					Integer numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error("error during getting total value of hits aggregated by - Exception: {}", e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}
					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAverageValueOfHitsAggregatedBy(String aKpiName,
			String aDateOfAggregationFilterCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_F(aKpiName.toString(),
					aDateOfAggregationFilterCodeLetter, MetricStatics.JSON_DATA_09_VALUE_KPI_AGGREG_BY_AVERAGE_VALUE);

			SearchResult aSearchResult = this.executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();

				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					int numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						ObjectMapper anObjectMapper = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);

						try {
							Map<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error("error during getting average value of hits aggregated by - Exception: {}", e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getTotalValueOfHitsAggregatedByInLast(String aKpiName,
			String aDateOfAggregationFilterCodeLetter, String aDateCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_G(aKpiName.toString(),
					aDateOfAggregationFilterCodeLetter, aDateCodeLetter,
					MetricStatics.JSON_DATA_08_VALUE_KPI_AGGREG_BY_TOTAL_VALUE_AND_RETROACTIV);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					int numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error(
									"error during getting total value of hits aggregated by in last - Exception: {}",
									e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAverageValueOfHitsAggregatedByInLast(String aKpiName,
			String aDateOfAggregationFilterCodeLetter, String aDateCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_G(aKpiName.toString(),
					aDateOfAggregationFilterCodeLetter, aDateCodeLetter,
					MetricStatics.JSON_DATA_10_VALUE_KPI_AGGREG_BY_AVERAGE_VALUE_AND_RETROACTIV);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded ! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					int numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error(
									"error during getting average value of hits aggregated by in last - Exception: {}",
									e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getNbrAggregatedByWithEndStateInLast(String aKpiName,
			String aDateOfAggregationFilterCodeLetter, String aKpiEndStatus, String aDateCodeLetter) {
		if ((aKpiName == null) || (aDateOfAggregationFilterCodeLetter == null) || (aKpiEndStatus == null))
			return null;

		if ((aDateCodeLetter == null))
			return null;

		// @formatter:off
		List<Map<String, Object>> listeDesAgregations 	  = new ArrayList<>();
		List<Map<String, Object>> listeVideDesAgregations = new ArrayList<>();
		// @formatter:on

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_E(aKpiName.toString(), aKpiEndStatus,
					aDateOfAggregationFilterCodeLetter, aDateCodeLetter,
					MetricStatics.JSON_DATA_04_KPI_AGGREG_VALUES_WITH_STATUS_AND_RETROACTIV);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded ! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesAgregations;
				else {
					// @formatter:off
					JsonObject aJsonObject_aggregations    = aSearchResult.getJsonObject().get("aggregations")
							.getAsJsonObject();
					JsonObject aJsonObject_hit_overtime    = aJsonObject_aggregations.get("hit_over_time")
							.getAsJsonObject();
					JsonArray aJsonObject_bucketsJsonArray = aJsonObject_hit_overtime.get("buckets").getAsJsonArray();
					Integer tailleTableauResultats 		   = aJsonObject_bucketsJsonArray.size();
					// @formatter:on

					int numeroLigneAggregation = 0;
					while (numeroLigneAggregation < tailleTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper   = new ObjectMapper();
						JsonObject aJsonObjDeUneLigne = (JsonObject) aJsonObject_bucketsJsonArray
								.get(numeroLigneAggregation);
						// @formatter:on

						try {
							HashMap<String, Object> hashMapDeUneLigne = anObjectMapper
									.readValue(aJsonObjDeUneLigne.toString(), HashMap.class);

							listeDesAgregations.add(numeroLigneAggregation, hashMapDeUneLigne);
						} catch (Exception e) {
							logger.error(
									"error during getting number aggregated by with end state in last - Exception: {}",
									e);
						}

						numeroLigneAggregation = numeroLigneAggregation + 1;
					}

					return listeDesAgregations;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return null;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> getHitOccurrencesInLast(String aKpiName, String aDateCodeLetter) {
		return getOccurrencesSearchValue(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT,
				aDateCodeLetter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> getOccurrencesWithEndState(String aKpiName, String aKpiEndStatus) {
		return getOccurrencesSearchValueWithEndState(applicationName, aKpiName, MetricType.HIT, aKpiEndStatus, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> getOccurrencesWithEndStateInLast(String aKpiName, String aKpiEndStatus,
			String aDateCodeLetter) {
		return getOccurrencesSearchValueWithEndState(applicationName, aKpiName, MetricType.HIT, aKpiEndStatus,
				aDateCodeLetter);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveHit(String aKpiName) {
		saveMetric(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT, 1d, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveHit(String aKpiName, Double value) {
		saveMetric(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT, value, null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveStart(String aKpiName, String correlationId) {
		Map<String, Object> aKpiExtraInfo = new HashMap<>();

		aKpiExtraInfo.put("CorrelationId", correlationId);
		aKpiExtraInfo.put("Etat", "Started");

		saveHit(aKpiName, aKpiExtraInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveHit(String aKpiName, Map<String, Object> additionalMetricInfo) {
		saveMetric(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT, 1d, additionalMetricInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveEnd(String aKpiName, String correlationId, String aKpiEndStatus,
			Map<String, Object> additionalMetricInfo) {
		additionalMetricInfo.put("CorrelationId", correlationId);
		additionalMetricInfo.put("Etat", "Ended");
		additionalMetricInfo.put("Status", aKpiEndStatus);

		saveHit(aKpiName, additionalMetricInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveEnd(String aKpiName, String correlationId, String aKpiEndStatus) {
		Map<String, Object> aDetailInformationsMap = new HashMap<>();

		aDetailInformationsMap.put("CorrelationId", correlationId);
		aDetailInformationsMap.put("Etat", "Ended");
		aDetailInformationsMap.put("Status", aKpiEndStatus);

		saveHit(aKpiName, aDetailInformationsMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveState(String aKpiName, String correlationId, String aKpiStateStatus,
			Map<String, Object> additionalMetricInfo) {
		// Not implemented
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveState(String aKpiName, String correlationId, String aKpiStateStatus) {
		// Not implemented
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveHit(String aKpiName, Double value, Map<String, Object> additionalMetricInfo) {
		saveMetric(applicationName, targetEnvironmentName, aKpiName, MetricType.HIT, value, additionalMetricInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveStart(String aKpiName, String correlationId, Map<String, Object> additionalMetricInfo) {
		additionalMetricInfo.put("CorrelationId", correlationId);
		additionalMetricInfo.put("Etat", "Started");

		saveHit(aKpiName, additionalMetricInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDuration(String aKpiName, Double start, Double end, Map<String, Object> additionalMetricInfo) {
		// Not implemented
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMetric(String aKpiName, MetricType type, Double metricValue, Map<String, Object> metaDataMetric) {
		saveMetric(applicationName, targetEnvironmentName, aKpiName, type, metricValue, metaDataMetric);
	}

	/**
	 * save metric
	 * 
	 * @param applicationName
	 * @param targetEnvironmentName
	 * @param aKpiName
	 * @param type
	 * @param value
	 * @param metaData
	 */
	private void saveMetric(String applicationName, String targetEnvironmentName, String aKpiName, MetricType type,
			double value, Map<String, Object> metaData) {
		if (checkIndex()) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("@timestamp", getCurrentDateTime());
			map.put("kpimillis", System.currentTimeMillis());
			map.put("application", applicationName);
			map.put("environnement", targetEnvironmentName);
			map.put("qualifier", aKpiName);
			map.put("metric", type);
			map.put("value", value);
			map.put("meta", metaData);

			try {
				logger.debug("saving kpi to index {}", lastIndexName);

				jestClient.executeAsync(new Index.Builder(map).index(lastIndexName).type(ES_TYPE_NAME_FOR_KPI).build(),
						new KpiResultHandler());
			} catch (Exception e) {
				logger.error("error when saving kpi - Exception: {}", e);
			}
		} else
			logger.error("business kpi index problem, check the logs.");
	}

	/**
	 * get occurrences search value with end state
	 * 
	 * @param applicationName
	 * @param aKpiName
	 * @param type
	 * @param anEndState
	 * @param aCodeRetroTime
	 * @return
	 */
	@Timed
	@ExceptionMetered
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getOccurrencesSearchValueWithEndState(String applicationName, String aKpiName,
			MetricType type, String anEndState, String aCodeRetroTime) {
		// @formatter:off
		List<Map<String, Object>> listeDesOccurences 	 = new ArrayList<>();
		List<Map<String, Object>> listeVideDesOccurences = new ArrayList<>();
		// @formatter:on

		if ((applicationName == null) || (anEndState == null))
			return null;

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = (aCodeRetroTime != null)
					? getPersonalizedQuery_D(aKpiName.toString(), anEndState, aCodeRetroTime)
					: getPersonalizedQuery_D(aKpiName.toString(), anEndState,
							MetricStatics.JSON_DATA_01e_KPI_SEARCH_WITH_QUALIFIER_AND_STATUS);

			SearchResult aSearchResult = this.executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded ! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesOccurences;
				else {
					// @formatter:off
					JsonArray aJsonArrayOfOccurences = aJSonObject.get("hits").getAsJsonArray();
					Integer taileTableauResultats 	 = aJsonArrayOfOccurences.size();
					// @formatter:on

					int numeroOccurence = 0;
					while (numeroOccurence < taileTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper 		 = new ObjectMapper();
						JsonObject aJsonObjDeUneOccurence 	 = (JsonObject) aJsonArrayOfOccurences.get(numeroOccurence);
						JsonObject aJsonObjDuMetaDeOccurence = aJsonObjDeUneOccurence.get("_source").getAsJsonObject()
								.get("meta").getAsJsonObject();
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneOccurence = anObjectMapper
									.readValue(aJsonObjDuMetaDeOccurence.toString(), HashMap.class);

							listeDesOccurences.add(numeroOccurence, hashMapDeUneOccurence);
						} catch (Exception e) {
							logger.error("error during getting occurrences search value with end state - Exception: {}",
									e);
						}

						numeroOccurence = numeroOccurence + 1;
					}

					return listeDesOccurences;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return listeVideDesOccurences;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return listeVideDesOccurences;
	}

	/**
	 * get occurrences search value
	 * 
	 * @param applicationName
	 * @param targetEnvironmentName
	 * @param aKpiName
	 * @param type
	 * @param aCodeRetroTime
	 * @return
	 */
	@Timed
	@ExceptionMetered
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getOccurrencesSearchValue(String applicationName, String targetEnvironmentName,
			String aKpiName, MetricType type, String aCodeRetroTime) {
		// @formatter:off
		List<Map<String, Object>> listeDesOccurences 	 = new ArrayList<>();
		List<Map<String, Object>> listeVideDesOccurences = new ArrayList<>();
		// @formatter:on

		if ((applicationName == null) || (targetEnvironmentName == null) || (aKpiName == null) || (type == null))
			return null;

		if (checkIndex()) {
			String aKpiPersonnalisedQuery;
			if ((aCodeRetroTime == null) || (aCodeRetroTime == "0"))
				aKpiPersonnalisedQuery = getPersonalizedQuery(aKpiName.toString());
			else
				aKpiPersonnalisedQuery = getPersonalizedQuery(aKpiName.toString(), aCodeRetroTime,
						MetricStatics.JSON_DATA_01b_KPI_SEARCH_WITH_QUALIFIER_AND_RETROACTIV_CODE);

			SearchResult aSearchResult = this.executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded ! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurences == 0)
					return listeVideDesOccurences;
				else {
					// @formatter:off
					JsonArray aJsonArrayOfOccurences = aJSonObject.get("hits").getAsJsonArray();
					Integer taileTableauResultats 	 = aJsonArrayOfOccurences.size();
					// @formatter:on

					Integer numeroOccurence = 0;
					while (numeroOccurence < taileTableauResultats) {
						// @formatter:off
						ObjectMapper anObjectMapper 	  = new ObjectMapper();
						JsonObject aJsonObjDeUneOccurence = (JsonObject) aJsonArrayOfOccurences.get(numeroOccurence);
						JsonObject aJsonObjDu_Source 	  = aJsonObjDeUneOccurence.get("_source").getAsJsonObject();
						// @formatter:on

						try {
							Map<String, Object> hashMapDeUneOccurence = anObjectMapper
									.readValue(aJsonObjDu_Source.toString(), HashMap.class);

							listeDesOccurences.add(numeroOccurence, hashMapDeUneOccurence);
						} catch (Exception e) {
							logger.error("error during getting occurrences search values - Exception: {}", e);
						}

						numeroOccurence = numeroOccurence + 1;
					}

					return listeDesOccurences;
				}
			} else {
				logger.debug("Query failed! index {}", lastIndexName);
				return listeVideDesOccurences;
			}
		} else {
			logger.error("business kpi index problem, check the logs.");
		}

		return listeVideDesOccurences;
	}

	/**
	 * get metric value
	 * 
	 * @param aKpiName
	 * @return
	 */
	@Timed
	@ExceptionMetered
	private Number getMetricValue(String aKpiName) {
		if (checkIndex()) {
			// @formatter:off
			String aKpiPersonnalisedQuery = getPersonalizedQuery(aKpiName.toString());
			SearchResult aSearchResult    = executeEsQuery(aKpiPersonnalisedQuery);
			// @formatter:on

			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				// @formatter:on

				return nbrOccurences;
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return 0;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return -1;
	}

	/**
	 * get quantity search value with details
	 * 
	 * @param aKpiName
	 * @param kpiExtraInfoRequestList
	 * @param kpiExtraInfoResultList
	 * @return
	 */
	@Timed
	@ExceptionMetered
	private Integer getQuantitySearchValueWithDetails(String aKpiName, List<String> kpiExtraInfoRequestList,
			List<Map<String, Object>> kpiExtraInfoResultList) {
		if (checkIndex()) {
			// @formatter:off
			String aKpiPersonnalisedQuery = getPersonalizedQuery(aKpiName.toString());
			SearchResult aSearchResult 	  = executeEsQuery(aKpiPersonnalisedQuery);
			// @formatter:on

			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  = aJSonObject.get("total").getAsInt();
				
				Map<String, Object> kpiResultInfo01 = new HashMap<>();
				Map<String, Object> kpiResultInfo02 = new HashMap<>();
				Map<String, Object> kpiResultInfo03 = new HashMap<>();
				Map<String, Object> kpiResultInfo04 = new HashMap<>();
				// @formatter:on

				if (kpiExtraInfoRequestList != null)
					fillKPIMapsAndLists(kpiExtraInfoResultList, kpiResultInfo01, kpiResultInfo02, kpiResultInfo03);
				else
					fillKPIMapsAndLists(kpiExtraInfoResultList, kpiResultInfo01, kpiResultInfo02, kpiResultInfo03,
							kpiResultInfo04);

				return nbrOccurences;
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return 0;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return -1;
	}

	/**
	 * fill KPI maps and lists
	 * 
	 * @param kpiExtraInfoResultList
	 * @param kpiResultInfo01
	 * @param kpiResultInfo02
	 * @param kpiResultInfo03
	 * @param kpiResultInfo04
	 */
	private void fillKPIMapsAndLists(List<Map<String, Object>> kpiExtraInfoResultList,
			Map<String, Object> kpiResultInfo01, Map<String, Object> kpiResultInfo02,
			Map<String, Object> kpiResultInfo03, Map<String, Object> kpiResultInfo04) {
		kpiResultInfo01.put("fwCallID", "0023");
		kpiResultInfo01.put("carID", "CAR-001001");
		kpiResultInfo01.put("contractID", "FA00010163");
		kpiResultInfo01.put("uwYear", "2015-02");
		kpiResultInfo01.put("insuredName", "Eenl Endesa");
		kpiResultInfo01.put("RequesterName", "Olga Zeydina");
		kpiResultInfo01.put("RequestDate", "22/06/2015 16:02:25");
		kpiResultInfo01.put("CallStatus", "F");
		kpiResultInfo01.put("ExitCode", "Failed");

		kpiResultInfo02.put("fwCallID", "0024");
		kpiResultInfo02.put("carID", "CAR-001241");
		kpiResultInfo02.put("contractID", "FA00010962");
		kpiResultInfo02.put("uwYear", "2015-01");
		kpiResultInfo02.put("insuredName", "Pfizer");
		kpiResultInfo02.put("RequesterName", "Melanie Robinson");
		kpiResultInfo02.put("RequestDate", "23/07/2015 13:52:03");
		kpiResultInfo02.put("CallStatus", "F");
		kpiResultInfo02.put("ExitCode", "Failed");

		kpiResultInfo03.put("fwCallID", "0026");
		kpiResultInfo03.put("carID", "CAR-001190");
		kpiResultInfo03.put("contractID", "FA00010550");
		kpiResultInfo03.put("uwYear", "2014-06");
		kpiResultInfo03.put("insuredName", "Sanofi Aventis");
		kpiResultInfo03.put("RequesterName", "Maelle DANIEL");
		kpiResultInfo03.put("RequestDate", "22/06/2015 16:02:02");
		kpiResultInfo03.put("CallStatus", "P");
		kpiResultInfo03.put("ExitCode", "In Progress");

		kpiResultInfo04.put("fwCallID", "0026");
		kpiResultInfo04.put("carID", "CAR-001190");
		kpiResultInfo04.put("contractID", "FA00010550");
		kpiResultInfo04.put("uwYear", "2014-06");
		kpiResultInfo04.put("insuredName", "Sanofi Aventis");
		kpiResultInfo04.put("RequesterName", "Maelle DANIEL");
		kpiResultInfo04.put("RequestDate", "22/06/2015 16:02:02");
		kpiResultInfo04.put("CallStatus", "P");
		kpiResultInfo04.put("ExitCode", "In Progress");

		kpiExtraInfoResultList.add(0, kpiResultInfo01);
		kpiExtraInfoResultList.add(1, kpiResultInfo02);
		kpiExtraInfoResultList.add(2, kpiResultInfo03);
		kpiExtraInfoResultList.add(3, kpiResultInfo04);
	}

	/**
	 * fill KPI maps and lists
	 * 
	 * @param kpiExtraInfoResultList
	 * @param kpiResultInfo01
	 * @param kpiResultInfo02
	 * @param kpiResultInfo03
	 */
	private void fillKPIMapsAndLists(List<Map<String, Object>> kpiExtraInfoResultList,
			Map<String, Object> kpiResultInfo01, Map<String, Object> kpiResultInfo02,
			Map<String, Object> kpiResultInfo03) {
		kpiResultInfo01.put("carID", "006001001");
		kpiResultInfo01.put("catAnalyst", "003006003");

		kpiResultInfo02.put("carID", "007001002");
		kpiResultInfo02.put("catAnalyst", "004007006");

		kpiResultInfo03.put("carID", "006001002");
		kpiResultInfo03.put("catAnalyst", "005007002");

		kpiExtraInfoResultList.add(0, kpiResultInfo01);
		kpiExtraInfoResultList.add(1, kpiResultInfo02);
		kpiExtraInfoResultList.add(2, kpiResultInfo03);
	}

	/**
	 * get quantity search value with end state
	 * 
	 * @param applicationName
	 * @param targetEnvironmentName
	 * @param aKpiName
	 * @param type
	 * @param anEndState
	 * @return
	 */
	@Timed
	@ExceptionMetered
	private int getQuantitySearchValueWithEndState(String applicationName, String targetEnvironmentName,
			String aKpiName, MetricType type, String anEndState) {
		if ((applicationName == null) || (targetEnvironmentName == null) || (aKpiName == null) || (type == null)
				|| (anEndState == null))
			return -1;

		if (checkIndex()) {
			String aKpiPersonnalisedQuery = getPersonalizedQuery_D(aKpiName.toString(), anEndState,
					MetricStatics.JSON_DATA_01e_KPI_SEARCH_WITH_QUALIFIER_AND_STATUS);

			SearchResult aSearchResult = this.executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				// @formatter:off
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences 	   = aJSonObject.get("total").getAsInt();
				// @formatter:on

				return nbrOccurences;
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return 0;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return -1;
	}

	/**
	 * get quantity search value with correlationId
	 * 
	 * @param applicationName
	 * @param targetEnvironmentName
	 * @param aKpiName
	 * @param aCorrelationId
	 * @return
	 */
	@Timed
	@ExceptionMetered
	private Integer getQuantitySearchValueWithCorrelationId(String applicationName, String targetEnvironmentName,
			String aKpiName, String aCorrelationId) {
		if ((applicationName == null) || (applicationName == targetEnvironmentName)
				|| (aCorrelationId == targetEnvironmentName))
			return -1;

		if (checkIndex()) {
			String aKpiPersonnalisedQuery;
			aKpiPersonnalisedQuery = getPersonalizedQuery_C(aKpiName.toString(), aCorrelationId,
					MetricStatics.JSON_DATA_01c_KPI_SEARCH_WITH_QUALIFIER_AND_CORROLATION_ID);

			SearchResult aSearchResult = this.executeEsQuery(aKpiPersonnalisedQuery);
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded ! index {}", lastIndexName);
				JsonObject aJSonObject = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences = aJSonObject.get("total").getAsInt();

				return nbrOccurences;
			} else {
				logger.debug("Query failed ! index {}", lastIndexName);

				return 0;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return -1;
	}

	/**
	 * get quantity search value
	 * 
	 * @param aKpiName
	 * @param additionalMetricInfo
	 * @param aBackwardDateCode
	 * @return
	 */
	@Timed
	@ExceptionMetered
	private Integer getQuantitySearchValue(String aKpiName, Map<String, Object> additionalMetricInfo,
			String aBackwardDateCode) {
		if (checkIndex()) {
			// @formatter:off
			String aKpiPersonnalisedQuery = (aBackwardDateCode == null) ? 
											getPersonalizedQuery(aKpiName.toString()) : 
												getPersonalizedQuery(aKpiName.toString(), aBackwardDateCode,
														MetricStatics.JSON_DATA_01b_KPI_SEARCH_WITH_QUALIFIER_AND_RETROACTIV_CODE);

			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			
			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded! index {}", lastIndexName);

				Map<String, Object> kpiResultMap   = new HashMap<>();
				Map<String, Object> kpiExtraInfo01 = new HashMap<>();
				Map<String, Object> kpiExtraInfo02 = new HashMap<>();
				
				JsonObject aJSonObject 			   = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurences  			   = aJSonObject.get("total").getAsInt();

				kpiExtraInfo01.put("carID", "006001001");
				kpiExtraInfo01.put("catAnalyst", "003006003");
				kpiExtraInfo02.put("carID", "007001002");
				kpiExtraInfo02.put("catAnalyst", "004007006");
				kpiResultMap.put("01", kpiExtraInfo01);
				kpiResultMap.put("02", kpiExtraInfo02);

				additionalMetricInfo = kpiResultMap;

				return nbrOccurences;
			// @formatter:on
			} else {
				logger.debug("Query failed! index {}", lastIndexName);

				return 0;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return -1;
	}

	/**
	 * get value of metric
	 * 
	 * @param aKpiName
	 * @return
	 */
	@Timed
	@ExceptionMetered
	private Double getValueOfMetric(String aKpiName) {
		if (checkIndex()) {
			// @formatter:off
			String aKpiPersonnalisedQuery = getPersonalizedQuery(aKpiName.toString());
			SearchResult aSearchResult = executeEsQuery(aKpiPersonnalisedQuery);
			// @formatter:on

			if (aSearchResult.isSucceeded()) {
				logger.debug("Query succeded ! index {}", lastIndexName);
				// @formatter:off
				JsonObject aJSonObject 	   = aSearchResult.getJsonObject().get("hits").getAsJsonObject();
				Integer nbrOccurencesDuKpi = aJSonObject.get("total").getAsInt();
				// @formatter:on

				if (nbrOccurencesDuKpi == 0)
					return 0d;
				else {
					// @formatter:off
					JsonArray lesOccurencesJSon    = aJSonObject.get("hits").getAsJsonArray();
					JsonObject aOccurenceNumero_Un = (JsonObject) lesOccurencesJSon.get(0);
					JsonObject aOccurenceSource    = aOccurenceNumero_Un.get("_source").getAsJsonObject();

					return aOccurenceSource.get("value").getAsDouble();
					// @formatter:on
				}
			} else {
				logger.debug("Query failed ! index {}", lastIndexName);

				return 0d;
			}
		} else
			logger.error("business kpi index problem, check the logs.");

		return -1d;
	}

	/**
	 * check index
	 * 
	 * @return
	 */
	private Boolean checkIndex() {
		try {
			String actualIndexName = getCurrentIndexName();

			if (!actualIndexName.equals(lastIndexName))
				indexExists = false;

			if (!indexExists) {
				// @formatter:off
				JestResult existsResult = jestClient.execute(new IndicesExists.Builder(actualIndexName).build());
				indexExists 			= existsResult.getJsonObject().get("found").getAsBoolean();
				// @formatter:on

				if (indexExists)
					lastIndexName = actualIndexName;

				logger.debug("index: {}, isExists: {}", actualIndexName, indexExists);

				if (!indexExists) {
					JestResult createResult_CreationIndex = jestClient
							.execute(new CreateIndex.Builder(actualIndexName).settings(getEsNodeSettings()).build());

					if (createResult_CreationIndex.isSucceeded()) {
						// @formatter:off
						indexExists   = true;
						lastIndexName = actualIndexName;
						// @formatter:on

						logger.debug("created business kpi index '{}'", actualIndexName);
					} else
						logger.error("error when creating business kpi index : {}",
								ToStringBuilder.reflectionToString(createResult_CreationIndex));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return indexExists;
	}

	/**
	 * get current index name
	 * 
	 * @return
	 */
	private String getCurrentIndexName() {
		String actualIndexName = indexName;

		if (!StringUtils.isEmpty(indexDateFormat)) {
			// @formatter:off
			SimpleDateFormat sdf = new SimpleDateFormat(indexDateFormat, Locale.US);
			actualIndexName 	 = indexName.concat("-").concat(sdf.format(new Date()));
			// @formatter:on
		}

		return actualIndexName;
	}

	/**
	 * get ES Node settings
	 * 
	 * @return
	 */
	private Map<String, Object> getEsNodeSettings() {
		// @formatter:off
		Map<String, Object> map_settings 			  = new HashMap<String, Object>();
		Map<String, Object> map_analysis 			  = new HashMap<String, Object>();
		Map<String, Object> map_analyser 			  = new HashMap<String, Object>();
		Map<String, Object> map_kri_analyser 		  = new HashMap<String, Object>();
		Map<String, Object> map_kri_analyser_elements = new HashMap<String, Object>();
		
		map_settings
			.put("settings", map_analysis);
		
		map_analysis
			.put("analysis", map_analyser);
		
		map_analyser
			.put("analyser", map_kri_analyser);
		
		map_kri_analyser
			.put("cri_analyser", map_kri_analyser_elements);
		
		map_kri_analyser_elements
			.put("type", "custom");
		
		map_kri_analyser_elements
			.put("char_filter", "standard");
		
		map_kri_analyser_elements
			.put("tokenizer", "keyword");
		
		map_kri_analyser_elements
			.put("filter", "standard");
		
		return map_settings;
		// @formatter:on
	}

	/**
	 * get personalized query with 1 parameter
	 * 
	 * @param nameOfKpi
	 * @return
	 */
	private String getPersonalizedQuery(String nameOfKpi) {
		try {
			// @formatter:off
			String requeteJSonBrute 	   = MetricStatics.JSON_DATA_01_KPI_SEARCH_WITH_QUALIFIER;
			JsonElement aJsonElement_query = new JsonParser().parse(requeteJSonBrute);
			
			JsonElement aJsonElement_bool 	  = aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 	  = aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_match   = aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier = aJsonArray_deux_match.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier  = (JsonObject) JsonElement_qualifier.getAsJsonObject();
			
			aJsonObject_qualifier
				.remove("qualifier");
			
			aJsonObject_qualifier
				.addProperty("qualifier", nameOfKpi.toString());
			
			return aJsonElement_query.toString();
			// @formatter:on			
		} catch (Exception e) {
			logger.error("error when personalising JSON Query with KPI : {}", e);
		}

		return new String("");
	}

	/**
	 * get personalized query with 3 parameters
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_CodeRetroTime
	 * @param aTwoParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery(String parameter01_kpiName, String parameter02_CodeRetroTime,
			String aTwoParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 			 = new JsonParser().parse(aTwoParametersJSonQuery);
			JsonElement aJsonElement_bool  			 = aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must  			 = aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange = aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 		 = aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 		 = (JsonObject) JsonElement_qualifier.getAsJsonObject();
			
			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement JsonElement_Range 			 = aJsonArray_deux_matchEtUnRange.get(2).getAsJsonObject().get("range");
			JsonObject aJsonObject_Range 			 = (JsonObject) JsonElement_Range.getAsJsonObject();
			JsonObject aJsonObject_TimeStamp 		 = aJsonObject_Range.get("@timestamp").getAsJsonObject();
			String aValueOf_TSFrom 					 = aJsonObject_TimeStamp.get("from").getAsString();
			String aNewValueOf_TSFrom 				 = StringUtils.replace(aValueOf_TSFrom, "[YYYY]", parameter02_CodeRetroTime);

			aJsonObject_TimeStamp.remove("from");
			aJsonObject_TimeStamp.addProperty("from", aNewValueOf_TSFrom);

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception e) {
			logger.error("error when personalising JSON Query with KPI: {}", e);
		}

		return new String("");

	}

	/**
	 * get personalized query with 3 parameters - C
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_CorrelationId
	 * @param aTwoParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery_C(String parameter01_kpiName, String parameter02_CorrelationId,
			String aTwoParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 			    = new JsonParser().parse(aTwoParametersJSonQuery);
			JsonElement aJsonElement_bool 			    = aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 			    = aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange    = aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 		    = aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 		    = (JsonObject) JsonElement_qualifier.getAsJsonObject();

			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement JsonElement_meta_CoorrelationId = aJsonArray_deux_matchEtUnRange.get(2).getAsJsonObject()
					.get("match");
			JsonObject aJsonObject_meta_CoorrelationId  = (JsonObject) JsonElement_meta_CoorrelationId.getAsJsonObject();

			aJsonObject_meta_CoorrelationId.remove("meta.CorrelationId");
			aJsonObject_meta_CoorrelationId.addProperty("meta.CorrelationId", parameter02_CorrelationId.toString());

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception e) {
			logger.error("error when personalising JSON Query with KPI: {}", e);
		}

		return new String("");
	}

	/**
	 * get personalized query with 3 parameters - D
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_EndState
	 * @param aParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery_D(String parameter01_kpiName, String parameter02_EndState,
			String aParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 				= new JsonParser().parse(aParametersJSonQuery);
			JsonElement aJsonElement_bool 				= aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 				= aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange 	= aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 			= aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 			= (JsonObject) JsonElement_qualifier.getAsJsonObject();

			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement JsonElement_meta_CoorrelationId = aJsonArray_deux_matchEtUnRange.get(2).getAsJsonObject()
					.get("match");
			JsonObject aJsonObject_meta_CoorrelationId  = (JsonObject) JsonElement_meta_CoorrelationId.getAsJsonObject();
			
			aJsonObject_meta_CoorrelationId.remove("meta.Status");
			aJsonObject_meta_CoorrelationId.addProperty("meta.Status", parameter02_EndState.toString());

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception e) {
			logger.error("error when personalising JSON Query with KPI: {}", e);
		}

		return new String("");

	}

	/**
	 * get personalized query with 3 parameters - F
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_CodeGroupeDateTime
	 * @param aTwoParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery_F(String parameter01_kpiName, String parameter02_CodeGroupeDateTime,
			String aTwoParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 			 = new JsonParser().parse(aTwoParametersJSonQuery);
			JsonElement aJsonElement_bool 			 = aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 			 = aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange = aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 		 = aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 		 = (JsonObject) JsonElement_qualifier.getAsJsonObject();

			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement aJsonElement_aggs 			 = aJsonElement_query.getAsJsonObject().get("aggs").getAsJsonObject();
			JsonElement aJsonElement_hit_over_time 	 = aJsonElement_aggs.getAsJsonObject().get("hit_over_time")
					.getAsJsonObject();
			JsonObject aJsonObject_hit_over_time 	 = aJsonElement_hit_over_time.getAsJsonObject();
			JsonObject aJsonObject_date_histogram 	 = aJsonObject_hit_over_time.get("date_histogram").getAsJsonObject();
			String aValueOf_interval 				 = aJsonObject_date_histogram.get("interval").getAsString();
			String aNewValueOf_interval 			 = StringUtils.replace(aValueOf_interval, "YYYY",
					parameter02_CodeGroupeDateTime);
			
			aJsonObject_date_histogram.remove("interval");
			aJsonObject_date_histogram.addProperty("interval", aNewValueOf_interval);

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception e) {
			logger.error("error when personalising JSON Query with KPI: {}", e);
		}

		return new String("");
	}

	/**
	 * get personalized query with 4 parameters - G
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_CodeGroupeDateTime
	 * @param parameter03_CodeRetroDateTime
	 * @param aTwoParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery_G(String parameter01_kpiName, String parameter02_CodeGroupeDateTime,
			String parameter03_CodeRetroDateTime, String aTwoParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 			 = new JsonParser().parse(aTwoParametersJSonQuery);
			JsonElement aJsonElement_bool 			 = aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 			 = aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange = aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 		 = aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 		 = (JsonObject) JsonElement_qualifier.getAsJsonObject();

			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement aJsonElement_aggs 			 = aJsonElement_query.getAsJsonObject().get("aggs").getAsJsonObject();
			JsonElement aJsonElement_hit_over_time 	 = aJsonElement_aggs.getAsJsonObject().get("hit_over_time")
					.getAsJsonObject();
			JsonObject aJsonObject_hit_over_time 	 = aJsonElement_hit_over_time.getAsJsonObject();
			JsonObject aJsonObject_date_histogram 	 = aJsonObject_hit_over_time.get("date_histogram").getAsJsonObject();
			String aValueOf_interval 				 = aJsonObject_date_histogram.get("interval").getAsString();
			String aNewValueOf_interval 			 = StringUtils.replace(aValueOf_interval, "YYYY",
					parameter02_CodeGroupeDateTime);

			aJsonObject_date_histogram.remove("interval");
			aJsonObject_date_histogram.addProperty("interval", aNewValueOf_interval);

			JsonElement JsonElement_Range 			 = aJsonArray_deux_matchEtUnRange.get(2).getAsJsonObject().get("range");
			JsonObject aJsonObject_Range 			 = (JsonObject) JsonElement_Range.getAsJsonObject();
			JsonObject aJsonObject_TimeStamp 		 = aJsonObject_Range.get("@timestamp").getAsJsonObject();
			String aValueOf_TSFrom 					 = aJsonObject_TimeStamp.get("from").getAsString();
			String aNewValueOf_TSFrom 				 = StringUtils.replace(aValueOf_TSFrom, "[UUUU]", parameter03_CodeRetroDateTime);

			aJsonObject_TimeStamp.remove("from");
			aJsonObject_TimeStamp.addProperty("from", aNewValueOf_TSFrom);

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception ex) {
			logger.error("error when personalising JSON Query with KPI: {}", ex);
		}

		return new String("");
	}

	/**
	 * get personalized query with 5 parameters - E
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_EndState
	 * @param parameter03_CodeGroupeDateTime
	 * @param parameter04_CodeRetroDateTime
	 * @param aTwoParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery_E(String parameter01_kpiName, String parameter02_EndState,
			String parameter03_CodeGroupeDateTime, String parameter04_CodeRetroDateTime,
			String aTwoParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 			 	= new JsonParser().parse(aTwoParametersJSonQuery);
			JsonElement aJsonElement_bool 			 	= aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 			 	= aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange 	= aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 		 	= aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 		    = (JsonObject) JsonElement_qualifier.getAsJsonObject();

			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement JsonElement_meta_CoorrelationId = aJsonArray_deux_matchEtUnRange.get(2).getAsJsonObject()
					.get("match");
			JsonObject aJsonObject_meta_CoorrelationId  = (JsonObject) JsonElement_meta_CoorrelationId.getAsJsonObject();

			aJsonObject_meta_CoorrelationId.remove("meta.Status");
			aJsonObject_meta_CoorrelationId.addProperty("meta.Status", parameter02_EndState.toString());

			JsonElement aJsonElement_aggs 				= aJsonElement_query.getAsJsonObject().get("aggs").getAsJsonObject();
			JsonElement aJsonElement_hit_over_time 		= aJsonElement_aggs.getAsJsonObject().get("hit_over_time")
					.getAsJsonObject();
			JsonObject aJsonObject_hit_over_time 		= aJsonElement_hit_over_time.getAsJsonObject();
			JsonObject aJsonObject_date_histogram 		= aJsonObject_hit_over_time.get("date_histogram").getAsJsonObject();
			String aValueOf_interval 					= aJsonObject_date_histogram.get("interval").getAsString();
			String aNewValueOf_interval 				= StringUtils.replace(aValueOf_interval, "YYYY",
					parameter03_CodeGroupeDateTime);
			
			aJsonObject_date_histogram.remove("interval");
			aJsonObject_date_histogram.addProperty("interval", aNewValueOf_interval);

			JsonElement JsonElement_Range 				= aJsonArray_deux_matchEtUnRange.get(3).getAsJsonObject().get("range");
			JsonObject aJsonObject_Range 				= (JsonObject) JsonElement_Range.getAsJsonObject();
			JsonObject aJsonObject_TimeStamp 			= aJsonObject_Range.get("@timestamp").getAsJsonObject();
			String aValueOf_TSFrom 						= aJsonObject_TimeStamp.get("from").getAsString();
			String aNewValueOf_TSFrom 					= StringUtils.replace(aValueOf_TSFrom, "[UUUU]", parameter04_CodeRetroDateTime);

			aJsonObject_TimeStamp.remove("from");
			aJsonObject_TimeStamp.addProperty("from", aNewValueOf_TSFrom);

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception e) {
			logger.error("error when personalising JSON Query with KPI: {}", e);
		}

		return new String("");
	}

	/**
	 * get personalized query with 4 parameters - E
	 * 
	 * @param parameter01_kpiName
	 * @param parameter02_EndState
	 * @param parameter03_CodeGroupeDateTime
	 * @param aTwoParametersJSonQuery
	 * @return
	 */
	private String getPersonalizedQuery_E(String parameter01_kpiName, String parameter02_EndState,
			String parameter03_CodeGroupeDateTime, String aTwoParametersJSonQuery) {
		try {
			// @formatter:off
			JsonElement aJsonElement_query 				= new JsonParser().parse(aTwoParametersJSonQuery);
			JsonElement aJsonElement_bool 				= aJsonElement_query.getAsJsonObject().get("query").getAsJsonObject();
			JsonElement aJsonElement_must 				= aJsonElement_bool.getAsJsonObject().get("bool").getAsJsonObject();
			JsonArray aJsonArray_deux_matchEtUnRange 	= aJsonElement_must.getAsJsonObject().get("must").getAsJsonArray();
			JsonElement JsonElement_qualifier 			= aJsonArray_deux_matchEtUnRange.get(0).getAsJsonObject().get("match");
			JsonObject aJsonObject_qualifier 			= (JsonObject) JsonElement_qualifier.getAsJsonObject();

			aJsonObject_qualifier.remove("qualifier");
			aJsonObject_qualifier.addProperty("qualifier", parameter01_kpiName.toString());

			JsonElement JsonElement_meta_CoorrelationId = aJsonArray_deux_matchEtUnRange.get(2).getAsJsonObject()
					.get("match");
			JsonObject aJsonObject_meta_CoorrelationId 	= (JsonObject) JsonElement_meta_CoorrelationId.getAsJsonObject();

			aJsonObject_meta_CoorrelationId.remove("meta.Status");
			aJsonObject_meta_CoorrelationId.addProperty("meta.Status", parameter02_EndState.toString());

			JsonElement aJsonElement_aggs 				= aJsonElement_query.getAsJsonObject().get("aggs").getAsJsonObject();
			JsonElement aJsonElement_hit_over_time 		= aJsonElement_aggs.getAsJsonObject().get("hit_over_time")
					.getAsJsonObject();
			JsonObject aJsonObject_hit_over_time 		= aJsonElement_hit_over_time.getAsJsonObject();
			JsonObject aJsonObject_date_histogram 		= aJsonObject_hit_over_time.get("date_histogram").getAsJsonObject();
			String aValueOf_interval 					= aJsonObject_date_histogram.get("interval").getAsString();
			String aNewValueOf_interval 				= StringUtils.replace(aValueOf_interval, "YYYY",
					parameter03_CodeGroupeDateTime);

			aJsonObject_date_histogram.remove("interval");
			aJsonObject_date_histogram.addProperty("interval", aNewValueOf_interval);

			return aJsonElement_query.toString();
			// @formatter:on
		} catch (Exception ex) {
			logger.error("error when personalising JSON Query with KPI: {}", ex);
		}

		return new String("");
	}

	/**
	 * execute Es query
	 * 
	 * @param aJsonTypeQuery
	 * @return
	 */
	private SearchResult executeEsQuery(String aJsonTypeQuery) {
		try {
			logger.debug("Executing a ES Query index {}  Type {}", lastIndexName, ES_TYPE_NAME_FOR_KPI);

			Search search = new Search.Builder(aJsonTypeQuery).addIndex(indexName.concat("-").concat("*"))
					.addType(ES_TYPE_NAME_FOR_KPI).build();

			return jestClient.execute(search);
		} catch (Exception e) {
			logger.error("error ES query: {}", e);
		}

		return null;
	}

}
