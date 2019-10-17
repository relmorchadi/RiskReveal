package com.scor.rr.importBatch.processing.domain;

/**
 * Created by U002629 on 19/02/2015.
 */
public class Period {
    private int nb;
    private int[]events;
    private int[]seqs;
//    private float[]quantiles;
    private double[]quantiles;
    private long[] eventDates;

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public int[] getEvents() {
        return events;
    }

    public void setEvents(int[] events) {
        this.events = events;
    }

    public int[] getSeqs() {
        return seqs;
    }

    public void setSeqs(int[] seqs) {
        this.seqs = seqs;
    }

//    public float[] getQuantiles() {
    public double[] getQuantiles() {
        return quantiles;
    }

//    public void setQuantiles(float[] quantiles) {
    public void setQuantiles(double[] quantiles) {
        this.quantiles = quantiles;
    }

    public long[] getEventDates() {
        return eventDates;
    }

    public void setEventDates(long[] eventDates) {
        this.eventDates = eventDates;
    }
}
