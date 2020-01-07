package com.scor.rr.service;

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
        Sort s = null;
        if (request.getSortConfig() != null && !request.getSortConfig().isEmpty()) {
            SortConfig firstCol = request.getSortConfig().get(0);

            s = new Sort(firstCol.getDirection().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, firstCol.getField());

            for (int i = 0; i < request.getSortConfig().size(); i++) {
                SortConfig tmp = request.getSortConfig().get(i);
                s = s.and(new Sort(tmp.getDirection().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, tmp.getField()));
            }
        }

        return s == null ? this.dashboardViewRepository.findAll(dashboardSpecification.getFilter(request.getFilterConfig()), PageRequest.of(request.getPageNumber(), request.getSize())) :
                this.dashboardViewRepository.findAll(dashboardSpecification.getFilter(request.getFilterConfig()), PageRequest.of(request.getPageNumber(), request.getSize(), s));
    }

}
