package com.scor.rr.service;

import com.scor.rr.domain.dto.DashBoardFilter;
import com.scor.rr.domain.dto.DashboardRequest;
import com.scor.rr.domain.dto.SortConfig;
import com.scor.rr.domain.entities.DashboardView;
import com.scor.rr.repository.DashboardViewRepository;
import com.scor.rr.repository.specification.DashboardSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;


@Component
public class DashboardService {

    @Autowired
    DashboardViewRepository dashboardViewRepository;

    @Autowired
    DashboardSpecification dashboardSpecification;

    public Page<DashboardView> getAll(DashboardRequest request) {

        SortConfig firstCol = request.getSortConfig().get(0);
        Sort s= null;
        if(!(firstCol == null)) {
            s = new Sort(firstCol.getDirection().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, firstCol.getField());

            for (int i = 0; i < request.getSortConfig().size(); i++) {
                SortConfig tmp = request.getSortConfig().get(i);
                s = s.and(new Sort(tmp.getDirection().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, tmp.getField()));
            }
        }

        return this.dashboardViewRepository.findAll(dashboardSpecification.getFilter(request.getFilterConfig()), PageRequest.of(request.getPageNumber(), request.getSize(), s));
    }

}
