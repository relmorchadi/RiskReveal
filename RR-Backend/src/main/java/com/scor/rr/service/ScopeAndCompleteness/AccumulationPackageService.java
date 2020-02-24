package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccumulationPackageService {

    @Autowired
    private AccumulationPackageRepository accumulationPackageRepository;
}
