package com.scor.rr.repository.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageOverrideSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccumulationPackageOverrideSectionRepository extends JpaRepository<AccumulationPackageOverrideSection,Long> {
}
