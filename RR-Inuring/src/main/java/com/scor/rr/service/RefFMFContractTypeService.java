package com.scor.rr.service;

import com.scor.rr.entity.RefFMFContractType;
import com.scor.rr.repository.RefFMFContractTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RefFMFContractTypeService {

    @Autowired
    private RefFMFContractTypeRepository refFMFContractTypeRepository;

    public List<RefFMFContractType> getAllContractTypes(){
        return refFMFContractTypeRepository.findAll();
    }
}
