package com.scor.rr.service;


import com.scor.rr.domain.*;
import com.scor.rr.repository.*;
import com.scor.rr.repository.specification.ContractSearchResultSpecification;
import com.scor.rr.repository.specification.FacContractSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class FacService {

    @Autowired
    FacContractRepository facContractRepository;

    @Autowired
    FacContractSpecification spec;

    @Autowired
    FacDatasourcesRepository facDatasourcesRepository;

    @Autowired
    FacRmsAnalysisBasicRepository facRmsAnalysisBasicRepository;

    @Autowired
    FacRmsAnalysisDetailRepository facRmsAnalysisDetailRepository;

    @Autowired
    FacRmsPortfolioRepository facRmsPortfolioRepository;

    public Page<FacContract> getFacContract(FacContract filter, Pageable pageable) {
        return facContractRepository.findAll(spec.getFilter(filter), pageable);
    }

    public FacContract saveFacContract(FacContract facContract) {
        ofNullable(facContract).ifPresent(ctr -> ctr.setId(null));
        return this.facContractRepository.save(facContract);
    }

    public List<FacDatasources> getDatasources() {
        return facDatasourcesRepository.findAll();
    }

    public List<FacRmsAnalysisBasic> findAnalysisBasic(Integer rdmId, String rdmName) {
        return facRmsAnalysisBasicRepository.findByRdmIdAndRdmName(rdmId, rdmName);
    }

    public List<FacRmsAnalysisDetail> findAnalysisDetail(Integer analysisId, String analysisName) {
        return facRmsAnalysisDetailRepository.findByAnalysisIdAndAnalysisName(analysisId, analysisName);
    }

    public List<FacRmsPortfolio> findPortfolio(Integer edmId, String edmName) {
        return facRmsPortfolioRepository.findByEdmIdAndEdmName(edmId, edmName);
    }
}
