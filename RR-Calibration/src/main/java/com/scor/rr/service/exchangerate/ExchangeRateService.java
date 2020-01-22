package com.scor.rr.service.exchangerate;

import com.scor.rr.domain.dto.ExchangeRatesRequest;
import com.scor.rr.repository.ExchangerateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Autowired
    ExchangerateRepository exchangerateRepository;

    public List<Map<String, Object>> getExchangeRates(ExchangeRatesRequest request) {
        return this.exchangerateRepository.findExchangeRatesBySourceCurrenciesAndEffectiveDate(
                request.getEffectiveDate(),
                String.join(",",request.getCurrencies())
        );
    }

}
