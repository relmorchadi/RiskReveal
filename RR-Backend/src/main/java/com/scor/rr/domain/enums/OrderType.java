package com.scor.rr.domain.enums;

public enum OrderType {
    asc("asc"), desc("desc");

    private String order;

    OrderType(String order) {
        this.order = order;
    }

    public String getOrderValue() {
        return order;
    }
}
