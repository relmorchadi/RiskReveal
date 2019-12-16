package com.scor.rr.request;

import com.scor.rr.JsonFormat.ExchangeRate;
import com.scor.rr.JsonFormat.Plts;
import lombok.Data;

import java.util.List;

@Data
public class InuringGroupingRequest {

    private List<Plts> plts;
    private String targetCcy;
    private List<ExchangeRate> exchangeRate;
    private String outcomePltName;
    private String groupingCriteria;
    private int userId;
}
