package com.scor.rr.rest.constants;

import com.scor.rr.service.constants.ConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/constants")
public class Constants {

    @Autowired
    ConstantsService constantsService;

    @GetMapping("currency/all")
    public List<String> getAllCurrencies() {
        return this.constantsService.getAllCurrencies();
    }
}
