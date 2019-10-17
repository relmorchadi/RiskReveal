package com.scor.rr.importBatch.processing.domain;

import java.util.Objects;

/**
 * Created by U002629 on 23/04/2015.
 */
public class Message implements Comparable {
    private final int index;
    private final String process;
    private final String description;

    public Message(int index, String process, String description) {
        this.index = index;
        this.process = process;
        this.description = description;
    }

    public int getIndex() {
        return index;
    }

    public String getProcess() {
        return process;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(index, message.index) &&
                Objects.equals(process, message.process) &&
                Objects.equals(description, message.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, process, description);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("index=").append(index);
        sb.append(", process='").append(process).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 1;
        Message message = (Message) o;
        return Integer.valueOf(index).compareTo(((Message) o).index);
    }
}
