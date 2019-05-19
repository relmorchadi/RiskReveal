package com.scor.rr.repository.specification;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.PltManagerView_;
import com.scor.rr.domain.dto.PltFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import static java.util.Optional.ofNullable;


public class PltTableSpecification {

    public static Specification<PltManagerView> filterPltTable(PltFilter pltFilter) {
        return Specification
                .where(ofNullable(pltFilter.getPltId()).map(id -> assertIsLike(PltManagerView_.pltId, id)).orElse(null))
                .and(ofNullable(pltFilter.getPltName()).map(name -> assertIsLike(PltManagerView_.pltName, name)).orElse(null))
                .and(ofNullable(pltFilter.getPeril()).map(peril -> assertIsLike(PltManagerView_.peril, peril)).orElse(null))
                .and(ofNullable(pltFilter.getRegionPerilCode()).map(rpCode -> assertIsLike(PltManagerView_.regionPerilCode, rpCode)).orElse(null))
                .and(ofNullable(pltFilter.getRegionPerilName()).map(rpName -> assertIsLike(PltManagerView_.regionPerilName, rpName)).orElse(null))
                .and(ofNullable(pltFilter.getGrain()).map(grain -> assertIsLike(PltManagerView_.grain, grain)).orElse(null))
                .and(ofNullable(pltFilter.getVendorSystem()).map(vendorSystem -> assertIsLike(PltManagerView_.vendorSystem, vendorSystem)).orElse(null))
                .and(ofNullable(pltFilter.getRap()).map(rap -> assertIsLike(PltManagerView_.rap, rap)).orElse(null));
    }

    private static Specification<PltManagerView> assertIsLike(SingularAttribute<PltManagerView, ?> attr, String keyword) {
        return (Root<PltManagerView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(attr).as(String.class), "%" + keyword + "%");
        };
    }

    private static Specification<PltManagerView> assertIsEqual(SingularAttribute<PltManagerView, ?> attr, String keyword) {
        return (Root<PltManagerView> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(attr).as(String.class), keyword);
        };
    }

}
