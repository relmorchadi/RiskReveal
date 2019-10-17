package com.scor.rr.importBatch.processing.exposure.exposure;

import com.scor.rr.utils.ALMFUtils;
import com.scor.rr.utils.GenericDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExposureExtractor implements ResultSetExtractor<Boolean> {

	private static final Logger logger = LoggerFactory.getLogger(ExposureExtractor.class);

	private final static String DELIM = "\t";

	private File file;
	private List<GenericDescriptor> descriptors;

	public ExposureExtractor(List<GenericDescriptor> descriptors, File file) {
		this.file = file;
		this.descriptors = descriptors;

		// make sure the descriptors are already sorted or do it
		Collections.sort(this.descriptors);
	}

	public Boolean extractData(ResultSet rs) {
		ExtractorFormatter ef = new ExtractorFormatter();

		try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw);) {
			descriptors.forEach(descriptor -> {
				try {
					bw.write(descriptor.getTargetName());

					bw.write(DELIM);
				} catch (IOException e) {
					logger.error("error during writing descriptor target name : {}", e);
				}
			});

			bw.newLine();

			int count = 0;
			while (rs.next()) {
				descriptors.forEach(descriptor -> {
					String o = ef.format(descriptor.getColName(), descriptor.getDataType(),
							descriptor.getTargetFormat(), rs);
					try {
						if (ALMFUtils.isNotNull(o))
							bw.write(o);

						bw.write(DELIM);
					} catch (IOException e) {
						logger.error("error during writing descriptor colName, dataType and targetFormat : {}", e);
					}
				});

				bw.newLine();
				count++;
			}

			bw.flush();

			logger.debug("Read {} rows", count);
		} catch (Throwable th) {
			logger.error("", th);

			throw new RuntimeException(th);
		} finally {
			Map<String, Integer> exceptions = ef.getExceptions();

			if (ALMFUtils.isNotNull(exceptions) && ALMFUtils.isNotEmpty(exceptions.values())) {
				logger.warn("=== Exceptions in extract data ===");

				exceptions.entrySet().forEach(exception -> {
					logger.warn("{}", exception.getKey());

					logger.warn("==> found {} times", exception.getValue());
				});
			}
		}

		return null;
	}

	private static class ExtractorFormatter {

		private SimpleDateFormat sdf;
		private DecimalFormat df;
		private Map<String, Integer> exceptions;

		public ExtractorFormatter() {
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			Locale locale = new Locale("en", "US");
			df = (DecimalFormat) NumberFormat.getNumberInstance(locale);
			df.setGroupingUsed(false);
			df.setMinimumFractionDigits(1);
			df.setMinimumIntegerDigits(1);
		}

		private void onError(String colName, String dataType) {
			String ex = "Error when retrieving data columnName " + colName + " dataType " + dataType
					+ ": returned data is NULL";
			Integer count = getExceptions().get(ex);
			if (count == null) {
				count = 1;
			} else {
				count += 1;
			}
			getExceptions().put(ex, count);
		}

		public String format(String colName, String dataType, String targetFormat, ResultSet rs) {
			// @formatter:off
			try {
				switch (dataType.toLowerCase()) {
					case "int":
						int valInt = rs.getInt(colName);
						return "" + valInt;
					case "smallint":
						short valShort = rs.getShort(colName);
						return "" + valShort;
					case "bigint":
						long valBigInt = rs.getLong(colName);
						return "" + valBigInt;
					case "float":
					case "double":
						double valDouble = rs.getDouble(colName);
						return df.format(valDouble);
					case "numeric":
						BigDecimal valBigDecimal = rs.getBigDecimal(colName);
						if (valBigDecimal == null) {
							onError(colName, dataType);
							return "";
						}
						return df.format(valBigDecimal.doubleValue());
					case "bit":
						boolean valBool = rs.getBoolean(colName);
						return valBool ? "TRUE" : "FALSE";
					case "datetime":
					case "timestamp":
						Timestamp valDateTime = rs.getTimestamp(colName);
						if (valDateTime == null) {
							onError(colName, dataType);
							return "";
						}
						if (targetFormat != null && !targetFormat.isEmpty())
							sdf.applyPattern(targetFormat);
						return sdf.format(valDateTime);
					case "date":
						Date valDate = rs.getDate(colName);
						if (valDate == null) {
							onError(colName, dataType);
							return "";
						}
						if (targetFormat != null && !targetFormat.isEmpty())
							sdf.applyPattern(targetFormat);
						return sdf.format(valDate);
					case "time":
						Time valTime = rs.getTime(colName);
						if (valTime == null) {
							onError(colName, dataType);
							return "";
						}
						if (targetFormat != null && !targetFormat.isEmpty())
							sdf.applyPattern(targetFormat);
						return sdf.format(valTime);
					case "char":
					case "nvarchar":
					case "varchar":
						String valString = rs.getString(colName);
						return valString != null ? valString.replaceAll("\t\r\n", " ") : "";
					default:
						logger.warn("dataType {} not supported, retrieve data in column {} as an Object", dataType,
								colName);
						Object valObj = rs.getObject(colName);
						return valObj.toString();
				}
			} catch (Exception e) {
				String ex = "Error when retrieving data columnName " + colName + " dataType " + dataType
						+ " . Exception: " + e;
				Integer count = getExceptions().get(ex);
				if (count == null) {
					count = 1;
				} else {
					count += 1;
				}
				getExceptions().put(ex, count);
				return "";
			}
			// @formatter:on
		}

		public Map<String, Integer> getExceptions() {
			if (exceptions == null) {
				exceptions = new HashMap<>();
			}
			return exceptions;
		}

	}

}
