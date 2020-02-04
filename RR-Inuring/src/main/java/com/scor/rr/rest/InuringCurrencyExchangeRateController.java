package com.scor.rr.rest;

import com.scor.rr.exceptions.RRException;
import com.scor.rr.request.InuringExchangeRateVintageRequest;
import com.scor.rr.service.InuringExchangeRateVintageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inuring/CurrencyExchangeRate")
public class InuringCurrencyExchangeRateController {

    private InuringExchangeRateVintageService inuringExchangeRateVintageService;

//    @GetMapping("generate")
//    public double getExchangeRate(InuringExchangeRateVintageRequest request) throws RRException {
//         return inuringExchangeRateVintageService.getCurrencyExchangeRate(request);
//    }
}
