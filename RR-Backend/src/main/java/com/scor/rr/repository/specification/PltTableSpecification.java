package com.scor.rr.repository.specification;

import com.scor.rr.domain.TargetBuild.PLTManagerView;
import com.scor.rr.domain.TargetBuild.PLTManagerView_;
import com.scor.rr.domain.dto.PltFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import static java.util.Optional.ofNullable;

@Component
public class PltTableSpecification extends BaseSpecification<PLTManagerView,PltFilter> {

    public Specification<PLTManagerView> getFilter(PltFilter pltFilter) {
        return Specification
                .where(ofNullable(pltFilter.getPltId()).map(id -> assertIsLike(PLTManagerView_.pltId, id)).orElse(null))
                .and(ofNullable(pltFilter.getPltName()).map(name -> assertIsLike(PLTManagerView_.pltName, name)).orElse(null))
                .and(ofNullable(pltFilter.getRegionPerilCode()).map(rpCode -> assertIsLike(PLTManagerView_.regionPerilCode, rpCode)).orElse(null))
                .and(ofNullable(pltFilter.getGrain()).map(grain -> assertIsLike(PLTManagerView_.grain, grain)).orElse(null))
                .and(ofNullable(pltFilter.getVendorSystem()).map(vendorSystem -> assertIsLike(PLTManagerView_.vendorSystem, vendorSystem)).orElse(null));
    }

    private static Specification<PLTManagerView> assertIsLike(SingularAttribute<PLTManagerView, ?> attr, String keyword) {
        return (Root<PLTManagerView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword + "%");
        };
    }

    private static Specification<PLTManagerView> assertIsEqual(SingularAttribute<PLTManagerView, ?> attr, String keyword) {
        return (Root<PLTManagerView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(attr).as(String.class), keyword);
        };
    }

}
