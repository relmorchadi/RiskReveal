package com.scor.rr.repository.specification;

import com.scor.rr.domain.entities.PLTManager.PLTManagerAll;
import com.scor.rr.domain.requests.GridDataRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PLTManagerAllSpecification extends BaseSpecification<PLTManagerAll, GridDataRequest<PLTManagerAll>> {
    @Override
    public Specification<PLTManagerAll> getData(GridDataRequest<PLTManagerAll> request) {
        return null;
    }
}
