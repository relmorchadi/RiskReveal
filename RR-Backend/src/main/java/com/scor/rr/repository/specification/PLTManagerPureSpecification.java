package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.PLTManagerFilter;
import com.scor.rr.domain.entities.PLTManager.PLTManagerPure;
import com.scor.rr.domain.entities.PLTManager.PLTManagerPure_;
import com.scor.rr.domain.requests.GridDataRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class PLTManagerPureSpecification extends BaseSpecification<PLTManagerPure, GridDataRequest<PLTManagerFilter>> {

    @Override
    public Specification<PLTManagerPure> getData(GridDataRequest<PLTManagerFilter> request) {
        PLTManagerFilter filters = request.getFilterModel();
        return Specification
                .where(AttributeEquals(PLTManagerPure_.workspaceContextCode, filters.getWorkspaceContextCode()))
                .and(AttributeEquals(PLTManagerPure_.uwYear, filters.getUwYear()))
                .and(ofNullable(filters.getPltId()).map(attr -> AttributeEquals(PLTManagerPure_.pltId, attr)).orElse(null));
    }


}
