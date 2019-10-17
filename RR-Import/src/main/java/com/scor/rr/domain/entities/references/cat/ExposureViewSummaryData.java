package com.scor.rr.domain.entities.references.cat;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Created by U002629 on 10/03/2015.
 */
public class ExposureViewSummaryData implements Comparable {
    private static final String REPLACEMENT = "XxX";
    private static final String ESCAPEDDOT = "\\.";
    private static final String DOT = ".";
    private String title; //column name
    private Map<String, Double> tiv; //cells
    private Map<String, Double> avgTIV; //cells
    private Map<String, Integer> locationCount; //cells

    public ExposureViewSummaryData() {
        this.tiv = new TreeMap<>();
        this.avgTIV = new TreeMap<>();
        this.locationCount = new TreeMap<>();
    }

    public ExposureViewSummaryData(String title, Map<String, Double> tiv, Map<String, Double> avgTIV, Map<String, Integer> locationCount) {
        this.title = title;
        this.tiv = tiv;
        this.avgTIV = avgTIV;
        this.locationCount = locationCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Double> getTiv() {
        return tiv;
    }

    public void setTiv(Map<String, Double> tiv) {
        this.tiv = tiv;
    }

    public Map<String, Double> getAvgTIV() {
        return avgTIV;
    }

    public void setAvgTIV(Map<String, Double> avgTIV) {
        this.avgTIV = avgTIV;
    }

    public Map<String, Integer> getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(Map<String, Integer> locationCount) {
        this.locationCount = locationCount;
    }


    public Map<String, Double> convertedTiv() {
        Map<String, Double> result = new TreeMap<>();
        for (String key : tiv.keySet()) {
            result.put(key.replaceAll(REPLACEMENT, DOT), tiv.get(key));
        }
        return result;
    }

    public void convertTiv(Map<String, Double> tiv) {
        this.tiv = new TreeMap<>();
        for (String key : tiv.keySet()) {
            this.tiv.put(key.replaceAll(ESCAPEDDOT, REPLACEMENT), tiv.get(key));
        }
    }

    public Map<String, Double> convertedAvgTIV() {
        Map<String, Double> result = new TreeMap<>();
        for (String key : avgTIV.keySet()) {
            result.put(key.replaceAll(REPLACEMENT, DOT), avgTIV.get(key));
        }
        return result;
    }

    public void convertAvgTIV(Map<String, Double> avgTIV) {
        this.avgTIV = new TreeMap<>();
        for (String key : avgTIV.keySet()) {
            this.avgTIV.put(key.replaceAll(ESCAPEDDOT, REPLACEMENT), avgTIV.get(key));
        }
    }

    public Map<String, Integer> convertedCount() {
        Map<String, Integer> result = new TreeMap<>();
        for (String key : locationCount.keySet()) {
            result.put(key.replaceAll(REPLACEMENT, DOT), locationCount.get(key));
        }
        return result;
    }

    public void convertCount(Map<String, Integer> locationCount){
        this.locationCount = new TreeMap<>();
        for (String key : locationCount.keySet()) {
            this.locationCount.put(key.replaceAll(ESCAPEDDOT, REPLACEMENT), locationCount.get(key));
        }
    }


    public void resetTiv(){
        this.tiv = new TreeMap<>();
        }
    public void resetLocationCount(){
        this.avgTIV = new TreeMap<>();
        }
    public void resetAvgTIV(){
        this.locationCount = new TreeMap<>();
    }


    public Double tivValue(String metric){
        return tiv.get(metric.replaceAll(ESCAPEDDOT, REPLACEMENT));
    }
    public Double avgTivValue(String metric){
        return avgTIV.get(metric.replaceAll(ESCAPEDDOT, REPLACEMENT));
    }
    public Integer locationCountValue(String metric){
        return locationCount.get(metric.replaceAll(ESCAPEDDOT, REPLACEMENT));
    }

    public void putTivValue(String metric, Double value){
        tiv.put(metric.replaceAll(ESCAPEDDOT,REPLACEMENT), value);
    }
    public void putAvgTivValue(String metric, Double value){
        avgTIV.put(metric.replaceAll(ESCAPEDDOT,REPLACEMENT), value);
    }
    public void putLocationCountValue(String metric, Integer value){
        locationCount.put(metric.replaceAll(ESCAPEDDOT,REPLACEMENT), value);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ExposureViewSummaryData other = (ExposureViewSummaryData) obj;
        return Objects.equals(this.title, other.title)
                && Objects.equals(this.tiv, other.tiv);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExposureViewSummaryData{");
        sb.append("title='").append(title).append('\'');
        sb.append(", tiv=").append(tiv);
        sb.append(", avgTIV=").append(avgTIV);
        sb.append(", locationCount=").append(locationCount);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if(o!=null && o instanceof ExposureViewSummaryData){
            ExposureViewSummaryData g = (ExposureViewSummaryData)o;
            Double total=null;
            Double total2=null;
            if(tiv!=null) {
                //TODO : TOTAL ? check it later
                total = tiv.get("TOTAL");
            }
            if(g.tiv!=null) {
                total2 = g.tiv.get("TOTAL");
            }
            if(total==null){
                if(total2==null){
                    return 0;
                } else {
                    return -1;
                }
            }else{
                if(total2==null){
                    return 1;
                } else {
                    final int compare = Double.compare(total, total2);
                    return compare==0?-title.compareToIgnoreCase(g.title):compare;
                }
            }

        }
        return 1;
    }

}
