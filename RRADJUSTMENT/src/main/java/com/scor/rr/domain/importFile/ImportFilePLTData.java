package com.scor.rr.domain.importFile;

public class ImportFilePLTData {

    private int eventId;
    private int year;
    private int month;
    private int day;
    private int repetition;
    private float value;
    private float maxExposure;
    private String eventDate;

    public ImportFilePLTData() {
    }

    public ImportFilePLTData(int eventId, int year, int month, int day, int repetition, float value, float maxExposure) {
        this.eventId = eventId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.repetition = repetition;
        this.value = value;
        this.maxExposure = maxExposure;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getMaxExposure() {
        return maxExposure;
    }

    public void setMaxExposure(float maxExposure) {
        this.maxExposure = maxExposure;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "ImportFilePLTData{" +
                "eventId=" + eventId +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", repetition=" + repetition +
                ", value=" + value +
                ", maxExposure=" + maxExposure +
                '}';
    }
}
