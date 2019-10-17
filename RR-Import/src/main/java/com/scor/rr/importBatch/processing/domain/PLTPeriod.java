package com.scor.rr.importBatch.processing.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by U002629 on 05/03/2015.
 */
public class PLTPeriod implements Comparable {
    private final int period;
    private final List<PLTResult> results;

    public PLTPeriod(int period) {
        this.period = period;
        results = new ArrayList<>();
    }

    public int getPeriod() {
        return period;
    }

    public List<PLTResult> getResults() {
        return results;
    }

    public void addResult(PLTResult result){
        results.add(result);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(Integer.toString(period)).append(";")
                .append(results).append(";").toString();
    }

    @Override
    public int compareTo(Object o) {
        if(! (o instanceof PLTPeriod))
            return -1;

        PLTPeriod r = (PLTPeriod)o;
        // nope, no instantiation when doing heavy processing agains large pool of objects
        return (this.period - r.period);
    }


    @Override
    public int hashCode() {
        return Objects.hash(period, results);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PLTPeriod other = (PLTPeriod) obj;
        return (this.period == other.period) && this.results.equals(other.results);
    }
}
