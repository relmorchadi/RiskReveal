package com.scor.rr.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 * NumberFormatTools
 * 
 * @author HADDINI Zakariyae
 *
 */
public class NumberFormatUtils {

	private static final String PATTERN;
	private static final DecimalFormat DF;

	static {
		PATTERN = "############################.###########################";
		
		DF = new DecimalFormat(PATTERN, DecimalFormatSymbols.getInstance(Locale.US));
		DF.setGroupingUsed(false);
	}

	public synchronized static Double convertToDot(String value) {
		try {
			return DF.parse(value.replace("XxX", ".")).doubleValue();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized static String convertFromDot(Double value) {
		return DF.format(value).replaceAll("\\.", "XxX");
	}
}
