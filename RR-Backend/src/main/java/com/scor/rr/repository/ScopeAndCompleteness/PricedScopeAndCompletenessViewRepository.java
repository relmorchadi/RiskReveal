package com.scor.rr.repository.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.Views.PricedScopeAndCompletenessView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PricedScopeAndCompletenessViewRepository extends JpaRepository<PricedScopeAndCompletenessView,Long> {

    List<PricedScopeAndCompletenessView> findByProjectId(long projectID);
    PricedScopeAndCompletenessView findByPLTHeaderId(long pltHeaderId);
}
