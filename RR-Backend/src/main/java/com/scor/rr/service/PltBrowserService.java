package com.scor.rr.service;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.repository.PltManagerViewRepository;
import com.scor.rr.repository.specification.PltTableSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class PltBrowserService {

    @Autowired
    PltManagerViewRepository pltManagerViewRepository;
    @Autowired
    PltTableSpecification pltTableSpecification;

    public Page<PltManagerView> searchPltTable(PltFilter pltFilter, Pageable pageable){
        return pltManagerViewRepository.findAll(pltTableSpecification.getFilter(pltFilter),pageable);
    }

}
