package com.scor.rr.rest.exchangeRate;

import com.scor.rr.domain.dto.ExchangeRateResponse;
import com.scor.rr.domain.dto.ExchangeRatesRequest;
import com.scor.rr.service.exchangerate.ExchangeRateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/exchangerate")
public class ExchangeRate {

    @Autowired
    ExchangeRateService exchangeRateService;

    @PostMapping
    public List<ExchangeRateResponse> getExchangeRates(@RequestBody ExchangeRatesRequest request) {

        ModelMapper mapper = new ModelMapper();

        return this.exchangeRateService.getExchangeRates(request)
                .stream()
                .map(obj -> {
                    System.out.println(obj);
                    return mapper.map(obj, ExchangeRateResponse.class);
                })
                .collect(Collectors.toList());
    }
}
