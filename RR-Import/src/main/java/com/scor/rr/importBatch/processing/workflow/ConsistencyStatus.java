package com.scor.rr.importBatch.processing.workflow;

import java.util.Objects;

/**
 * Created by U002629 on 30/03/2015.
 */
public class ConsistencyStatus {
    public enum Status{
        OK, KO
    }

    private final Status status;
    private final String message;
    private final String checkName;

    public ConsistencyStatus(Status status, String message, String checkName) {
        this.status = status;
        this.message = message;
        this.checkName = checkName;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCheckName() {
        return checkName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, checkName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ConsistencyStatus other = (ConsistencyStatus) obj;
        return Objects.equals(this.status, other.status)
                && Objects.equals(this.message, other.message)
                && Objects.equals(this.checkName, other.checkName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConsistencyStatus{");
        sb.append("status=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append(", checkName='").append(checkName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
