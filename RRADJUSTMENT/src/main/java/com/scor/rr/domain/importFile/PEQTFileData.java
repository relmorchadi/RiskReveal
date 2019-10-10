package com.scor.rr.domain.importFile;

public class PEQTFileData {
    private int eventId;
    private int year;
    private String eventDate;

    public PEQTFileData() {
    }

    public PEQTFileData(int eventId, int year, String eventDate) {
        this.eventId = eventId;
        this.year = year;
        this.eventDate = eventDate;
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PEQTFileData)) return false;

        PEQTFileData that = (PEQTFileData) o;

        if (getEventId() != that.getEventId()) return false;
        if (getYear() != that.getYear()) return false;
        return getEventDate() != null ? getEventDate().equals(that.getEventDate()) : that.getEventDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getEventId();
        result = 31 * result + getYear();
        result = 31 * result + (getEventDate() != null ? getEventDate().hashCode() : 0);
        return result;
    }
}
