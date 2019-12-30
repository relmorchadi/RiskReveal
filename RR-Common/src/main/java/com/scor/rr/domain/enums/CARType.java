package com.scor.rr.domain.enums;

/**
 * Created by u004602 on 26/12/2019.
 */
public enum CARType {
    FAC("FAC", "Facultative"), TTY("TTY", "Treaty");
    String code;
    String description;

    CARType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CARType{");
        sb.append("code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
