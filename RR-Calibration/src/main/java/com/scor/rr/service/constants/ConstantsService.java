package com.scor.rr.service.constants;

import com.scor.rr.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstantsService {

    @Autowired
    CurrencyRepository currencyRepository;

    public List<String> getAllCurrencies() {
        return this.currencyRepository.findAllCurrencies();
    }

}
