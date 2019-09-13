package com.scor.rr.service;


import com.scor.rr.domain.FacContract;
import com.scor.rr.repository.FacContractRepository;
import com.scor.rr.repository.specification.ContractSearchResultSpecification;
import com.scor.rr.repository.specification.FacContractSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class FacService {

    @Autowired
    FacContractRepository facContractRepository;

    @Autowired
    FacContractSpecification spec;

    public Page<FacContract> getFacContract(FacContract filter, Pageable pageable) {
        return facContractRepository.findAll(spec.getFilter(filter), pageable);
    }
}
