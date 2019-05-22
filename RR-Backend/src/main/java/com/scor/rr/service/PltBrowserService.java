package com.scor.rr.service;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.repository.PltManagerViewRepository;
import com.scor.rr.repository.specification.PltTableSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PltBrowserService {

    @Autowired
    PltManagerViewRepository pltManagerViewRepository;
    @Autowired
    PltTableSpecification pltTableSpecification;

    public List<PltManagerView> searchPltTable(PltFilter pltFilter){
        return pltManagerViewRepository.findAll(pltTableSpecification.getFilter(pltFilter));
    }

}
