package com.scor.rr.utils;

import com.scor.rr.domain.entities.rms.RmsAnalysis;

import java.util.*;

/**
 * ALMFUtils
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ALMFUtils {

	/**
	 * check if a collection is not empty
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * check if an object is not null
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object) {
		return object != null;
	}

	/**
	 * check if a string is not null & is not empty
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNotEmpty(String object) {
		return object != null && !object.isEmpty();
	}

	/**
	 * translate date from sql to util
	 * 
	 * @param date
	 * @return
	 */
	public static Date translateDate(java.sql.Timestamp date) {
		if (isNotNull(date))
			return new Date(date.getTime());

		return null;
	}

	/**
	 * make source EPHeader hash key
	 * 
	 * @param analysisId
	 * @param fpCode
	 * @param fpTYLabelId
	 * @return
	 */
	public static String makeSourceEPHeaderHashKey(Long analysisId, String fpCode, Integer fpTYLabelId) {
		// @formatter:off
        String delimiter 	  = ":";
        StringBuilder builder = new StringBuilder().append(analysisId)
        										   .append(delimiter)
        										   .append(fpCode);
        
        if (isNotNull(fpTYLabelId))
            builder.append(delimiter)
            	   .append(fpTYLabelId);
        
        return builder.toString();
     // @formatter:on
	}

	/**
	 * map by analysis id
	 *
	 * @return
	 */
	public static Map<String, RmsAnalysis> mapByAnalysisId(List<RmsAnalysis> rmsAnalysises) {
		Map<String, RmsAnalysis> map = new HashMap<>();

		if (isNotNull(rmsAnalysises))
			rmsAnalysises.forEach(rmsAnalysis -> map.put(rmsAnalysis.getAnalysisId(), rmsAnalysis));

		return map;
	}

}
