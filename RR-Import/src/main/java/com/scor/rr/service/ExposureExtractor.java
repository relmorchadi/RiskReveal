package com.scor.rr.service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExposureExtractor implements ResultSetExtractor<Boolean> {
    private static final Logger log = LoggerFactory.getLogger(ExposureExtractor.class);
    private final static String DELIM = "\t";

    private List<GenericDescriptor> descriptors;
    private File file;


    public ExposureExtractor(List<GenericDescriptor> descriptors, File file) {
        this.file = file;
        this.descriptors = descriptors;
        // make sure the descriptors are already sorted or do it
        Collections.sort(descriptors);
    }

    @Override
    public Boolean extractData(ResultSet rs) {
        ExtractorFormatter ef = new ExtractorFormatter();
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);

            for (GenericDescriptor gd : descriptors) {
                bw.write(gd.getTargetName());
                bw.write(DELIM);
            }
            bw.newLine();

            int count = 0;

            while (rs.next()) {
                for (GenericDescriptor gd : descriptors) {
                    String o = ef.format(gd.getColName(), gd.getDataType(), gd.getTargetFormat(), rs);
                    if (o != null) {
                        bw.write(o);
                    }
                    bw.write(DELIM);
                }
                bw.newLine();
                count++;
            }
            bw.flush();

            log.debug("Read {} rows", count);
        } catch (Throwable th) {
            log.error("", th);
            throw new RuntimeException(th);
        } finally {
            Map<String, Integer> exceptions = ef.getExceptions();
            if (exceptions != null && !exceptions.isEmpty()) {
                log.warn("=== Exceptions in extract data ===");
                for (Map.Entry<String, Integer> ex : exceptions.entrySet()) {
                    log.warn("{}", ex.getKey());
                    log.warn("==> found {} times", ex.getValue());
                }
            }
            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(fw);
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
            String ex = "Error when retrieving data columnName " + colName + " dataType " + dataType +
                    ": returned data is NULL";
            Integer count = getExceptions().get(ex);
            if (count == null) {
                count = 1;
            } else {
                count += 1;
            }
            getExceptions().put(ex, count);
        }

        //TODO: using OutputColStringFormat
        public String format(String colName, String dataType, String targetFormat, ResultSet rs) {
            try {
                // TODO : check null
                switch (dataType.toLowerCase()) {
                    case "int":
                        int valInt = rs.getInt(colName);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valInt);
                        return "" + valInt;
                    case "smallint":
                        short valShort = rs.getShort(colName);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valShort);
                        return "" + valShort;
                    case "bigint":
                        long valBigInt = rs.getLong(colName);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valInt);
                        return "" + valBigInt;
                    case "float":
                    case "double":
                        double valDouble = rs.getDouble(colName);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valDouble);
                        return df.format(valDouble);
                    case "numeric":
                        BigDecimal valBigDecimal = rs.getBigDecimal(colName);
                        if (valBigDecimal == null) {
                            onError(colName, dataType);
                            return "";
                        }
//						log.debug("colName {} dataType {} val {}", colName, dataType, valBigDecimal);
                        return df.format(valBigDecimal.doubleValue());
                    case "bit":
                        boolean valBool = rs.getBoolean(colName);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valBool);
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
//						log.debug("colName {} dataType {} val {}", colName, dataType, valDateTime);
                        return sdf.format(valDateTime);
                    case "date":
                        Date valDate = rs.getDate(colName);
                        if (valDate == null) {
                            onError(colName, dataType);
                            return "";
                        }
                        if (targetFormat != null && !targetFormat.isEmpty())
                            sdf.applyPattern(targetFormat);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valDate);
                        return sdf.format(valDate);
                    case "time":
                        Time valTime = rs.getTime(colName);
                        if (valTime == null) {
                            onError(colName, dataType);
                            return "";
                        }
                        if (targetFormat != null && !targetFormat.isEmpty())
                            sdf.applyPattern(targetFormat);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valTime);
                        return sdf.format(valTime);
                    case "char":
                    case "nvarchar":
                    case "varchar":
                        String valString = rs.getString(colName);
//						log.debug("colName {} dataType {} val {}", colName, dataType, valString);
                        return valString != null ? valString.replaceAll("\t\r\n", " ") : "";
                    default:
                        log.warn("dataType {} not supported, retrieve data in column {} as an Object", dataType, colName);
                        Object valObj = rs.getObject(colName);
                        return valObj.toString();
                }
            } catch (Exception e) {
                String ex = "Error when retrieving data columnName " + colName + " dataType " + dataType +
                        " . Exception: " + e;
                Integer count = getExceptions().get(ex);
                if (count == null) {
                    count = 1;
                } else {
                    count += 1;
                }
                getExceptions().put(ex, count);
                return "";
            }
        }

        public Map<String, Integer> getExceptions() {
            if (exceptions == null) {
                exceptions = new HashMap<>();
            }
            return exceptions;
        }

        public void setExceptions(Map<String, Integer> exceptions) {
            this.exceptions = exceptions;
        }
    }
}

