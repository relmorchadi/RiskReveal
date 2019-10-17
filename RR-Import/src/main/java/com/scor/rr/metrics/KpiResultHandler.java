package com.scor.rr.metrics;

import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KPI Result Handler
 * 
 * @author HADDINI Zakariyae
 *
 */
public class KpiResultHandler implements JestResultHandler<JestResult> {

	private static Logger logger = LoggerFactory.getLogger(KpiResultHandler.class);

	@Override
	public void completed(JestResult result) {
		if (result.isSucceeded()) {
			logger.info("---------------------");
			logger.debug("saved business kpi {}", ToStringBuilder.reflectionToString(result));
			logger.debug("Resultat JSon :{}", ToStringBuilder.reflectionToString(result));
			logger.debug("{}", result.getJsonString());
			logger.debug("---------------------");
		} else
			logger.debug("failed saving/or/getting business kpi failed {}", ToStringBuilder.reflectionToString(result));
	}

	@Override
	public void failed(Exception e) {
		logger.warn("save business kpi failed with exception: {}", e);
	}
}