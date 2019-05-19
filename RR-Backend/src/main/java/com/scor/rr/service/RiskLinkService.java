package com.scor.rr.service;


import com.scor.rr.domain.EdmRdm;
import com.scor.rr.repository.EdmRdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RiskLinkService {

    @Autowired
    EdmRdmRepository edmRdmRepository;

    public Page<EdmRdm> searchEdmRdm(String keyword, Pageable pageable) {
        return keyword==null ? edmRdmRepository.findAll(pageable) :  edmRdmRepository.findByNameLike("%" + keyword + "%", pageable);
    }


}
