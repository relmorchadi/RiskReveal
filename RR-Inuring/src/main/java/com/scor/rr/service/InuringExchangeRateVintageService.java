//package com.scor.rr.service;
//
//import com.scor.rr.entity.InuringExchangeRate;
//import com.scor.rr.exceptions.RRException;
//import com.scor.rr.exceptions.inuring.ExchangeRateTypeNotFoundException;
//import com.scor.rr.repository.InuringExchangeRateVintageRepository;
//import com.scor.rr.request.InuringExchangeRateVintageRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class InuringExchangeRateVintageService {
//
//    @Autowired
//    private InuringExchangeRateVintageRepository inuringExchangeRateVintageRepository;
//
//    public double getCurrencyExchangeRate(InuringExchangeRateVintageRequest request) throws RRException {
//        if(request.getType() == null || request.getType().equals("")){
//           request.setType("YEARLY");
//        }
//        if(!request.getType().equals("YEARLY") && !request.getType().equals("MONTHLY") && !request.getType().equals("QUARTERLY")) throw new ExchangeRateTypeNotFoundException(request.getType());
//
//
//
//        List<InuringExchangeRate> exchangeRates = inuringExchangeRateVintageRepository.getExchangeRate();
//    }
//}
