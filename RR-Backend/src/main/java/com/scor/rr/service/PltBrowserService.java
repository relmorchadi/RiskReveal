package com.scor.rr.service;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.dto.PltFilter;
import com.scor.rr.repository.PltManagerViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import static com.scor.rr.repository.specification.PltTableSpecification.filterPltTable;


@Component
public class PltBrowserService {

    @Autowired
    PltManagerViewRepository pltManagerViewRepository;

    public Page<PltManagerView> searchPltTable(PltFilter pltFilter, Pageable pageable){
        return pltManagerViewRepository.findAll(filterPltTable(pltFilter),pageable);
    }

}
