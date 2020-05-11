package com.scor.rr.service;

import com.scor.rr.domain.Response.GridDataResponse;
import com.scor.rr.domain.dto.ColumnFilter;
import com.scor.rr.domain.dto.PLTManagerFilter;
import com.scor.rr.domain.entities.PLTManager.PLTManagerAll;
import com.scor.rr.domain.entities.PLTManager.PLTManagerPure;
import com.scor.rr.domain.requests.GridDataRequest;
import com.scor.rr.repository.PLTManager.PLTManagerAllRepository;
import com.scor.rr.repository.PLTManager.PLTManagerPureRepository;
import com.scor.rr.repository.PLTManager.PLTManagerThreadRepository;
import com.scor.rr.repository.specification.PLTManagerPureSpecification;
import com.scor.rr.repository.specification.PLTManagerSpecificationBuilder;
import com.scor.rr.repository.specification.PLTManagerThreadSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

    @Autowired
    PLTManagerAllRepository pltManagerAllRepository;

    @Autowired
    EntityManager em;



    public  GridDataResponse getGroupedPLTs(GridDataRequest<List<ColumnFilter>> request) {
//        int pageNumber = (int) Math.round(Math.floor((double) request.getStartRow() / request.getEndRow()));
//        int pageSize = request.getEndRow() - request.getStartRow();
//        Page<PLTManagerPure> purePage = pureRepository.findAll(pureSpecification.getData(request), PageRequest.of(pageNumber, pageSize));
//        List<PLTManagerPure> pures = purePage.getContent();
////        request.setPureIds(
////                pures.stream()
////                .map(PLTManagerPure::getPltId)
////                .collect(Collectors.toList())
////        );

        PLTManagerSpecificationBuilder builder = new PLTManagerSpecificationBuilder(
                request,
                em
        );


        List<Object> d = builder.getResultList(true);
        long count = builder.getCount();
        return new GridDataResponse(d, count);
    }
}
