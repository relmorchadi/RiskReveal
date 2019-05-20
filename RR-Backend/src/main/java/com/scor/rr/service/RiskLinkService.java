package com.scor.rr.service;


import com.scor.rr.domain.AnalysisView;
import com.scor.rr.domain.EdmRdm;
import com.scor.rr.domain.PortfolioView;
import com.scor.rr.domain.dto.AnalysisFilter;
import com.scor.rr.domain.dto.PortfolioFilter;
import com.scor.rr.repository.AnalysisRepository;
import com.scor.rr.repository.EdmRdmRepository;
import com.scor.rr.repository.PortfolioRepository;
import com.scor.rr.repository.specification.AnalysisSpecification;
import com.scor.rr.repository.specification.PortfolioSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class RiskLinkService {

    @Autowired
    EdmRdmRepository edmRdmRepository;

    @Autowired
    AnalysisRepository analysisRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    AnalysisSpecification analysisSpecification;

    @Autowired
    PortfolioSpecification portfolioSpecification;

    public Page<EdmRdm> searchEdmRdm(String keyword, Pageable pageable) {
        return keyword==null ? edmRdmRepository.findAll(pageable) :  edmRdmRepository.findByNameLike("%" + keyword + "%", pageable);
    }


    public Page<AnalysisView> searchAnalysis(AnalysisFilter filter, Pageable pageable){
        return analysisRepository.findAll(analysisSpecification.getFilter(filter),pageable);
    }

    public Page<PortfolioView> searchPortflio(PortfolioFilter filter, Pageable pageable){
        return portfolioRepository.findAll(portfolioSpecification.getFilter(filter),pageable);
    }


}
