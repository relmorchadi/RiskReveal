package com.scor.rr.domain.requests;

import lombok.Data;

@Data
public class OrderAndVisibilityRequest {

    private long columnId;
    private int order;
    private boolean visible;

    public OrderAndVisibilityRequest() {
    }

    public OrderAndVisibilityRequest(long columnId, int order, boolean visible) {
        this.columnId = columnId;
        this.order = order;
        this.visible = visible;
    }
}
