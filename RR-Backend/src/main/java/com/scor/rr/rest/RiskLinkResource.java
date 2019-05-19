package com.scor.rr.rest;

import com.scor.rr.domain.EdmRdm;
import com.scor.rr.service.RiskLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/risk-link")
public class RiskLinkResource {

    @Autowired
    RiskLinkService riskLinkService;

    @GetMapping("edm-rdm")
    public Page<EdmRdm> searchEdmRdm(String keyword, @PageableDefault(size = 100) Pageable pageable) {
        return riskLinkService.searchEdmRdm(keyword, pageable);
    }

}
