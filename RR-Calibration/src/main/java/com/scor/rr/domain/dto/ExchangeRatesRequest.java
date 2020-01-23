package com.scor.rr.domain.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExchangeRatesRequest {
    List<String> currencies;
    Date effectiveDate;
}
