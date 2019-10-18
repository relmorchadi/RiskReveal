package com.scor.rr.rest;

import com.scor.rr.domain.AnalysisView;
import com.scor.rr.domain.EdmRdm;
import com.scor.rr.domain.PortfolioView;
import com.scor.rr.domain.dto.AnalysisFilter;
import com.scor.rr.domain.dto.PortfolioFilter;
import com.scor.rr.domain.views.AnalysisDetailView;
import com.scor.rr.service.RiskLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/risk-link")
public class RiskLinkResource {

    @Autowired
    RiskLinkService riskLinkService;

    @GetMapping("edm-rdm")
    public Page<EdmRdm> searchEdmRdm(String keyword, @PageableDefault(size = 100) Pageable pageable) {
        return riskLinkService.searchEdmRdm(keyword, pageable);
    }

    @GetMapping("analysis")
    public Page<AnalysisView> searchAnalysis(AnalysisFilter filter, @PageableDefault Pageable pageable){
        return riskLinkService.searchAnalysis(filter, pageable);
    }

    @GetMapping("detailed-analysis-scan")
    public List<AnalysisDetailView> searchAnalysis(@RequestParam Double analysisId, @RequestParam String analysisName){
        return riskLinkService.analysisDetailsScan(analysisId, analysisName);
    }



    @GetMapping("portfolio")
    public Page<PortfolioView> searchPortfolio(PortfolioFilter filter, @PageableDefault Pageable pageable){
        return riskLinkService.searchPortflio(filter, pageable);
    }

}
