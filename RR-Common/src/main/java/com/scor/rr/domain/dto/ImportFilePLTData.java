package com.scor.rr.domain.dto;

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
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getRepetition() {
        return this.repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getMaxExposure() {
        return this.maxExposure;
    }

    public void setMaxExposure(float maxExposure) {
        this.maxExposure = maxExposure;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String toString() {
        return "ImportFilePLTData{eventId=" + this.eventId + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", repetition=" + this.repetition + ", value=" + this.value + ", maxExposure=" + this.maxExposure + '}';
    }
}
