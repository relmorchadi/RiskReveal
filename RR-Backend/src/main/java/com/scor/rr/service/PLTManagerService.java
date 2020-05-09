package com.scor.rr.service;

import com.scor.rr.domain.Response.GridDataResponse;
import com.scor.rr.domain.dto.PLTManagerFilter;
import com.scor.rr.domain.entities.PLTManager.PLTManagerPure;
import com.scor.rr.domain.requests.GridDataRequest;
import com.scor.rr.repository.PLTManager.PLTManagerPureRepository;
import com.scor.rr.repository.PLTManager.PLTManagerThreadRepository;
import com.scor.rr.repository.specification.PLTManagerPureSpecification;
import com.scor.rr.repository.specification.PLTManagerThreadSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PLTManagerService {

    @Autowired
    PLTManagerPureRepository pureRepository;

    @Autowired
    PLTManagerThreadRepository threadRepository;

    @Autowired
    PLTManagerPureSpecification pureSpecification;

    @Autowired
    PLTManagerThreadSpecification threadSpecification;



    public  GridDataResponse getGroupedPLTs(GridDataRequest<PLTManagerFilter> request) {
        int pageNumber = (int) Math.round(Math.floor((double) request.getStartRow() / request.getEndRow()));
        List<PLTManagerPure> pures = pureRepository.findAll(pureSpecification.getData(request), PageRequest.of(pageNumber, request.getEndRow())).getContent();
        request.setPureIds(
                pures.stream()
                .map(PLTManagerPure::getPltId)
                .collect(Collectors.toList())
        );
        List<Object> d = new ArrayList<>(pures);
        d.addAll(threadRepository.findAll(threadSpecification.getData(request)));
        return new GridDataResponse(d);
    }
}
