package com.scor.rr.repository.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageAttachedPLT;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccumulationPackageAttachedPLTRepository extends JpaRepository<AccumulationPackageAttachedPLT,Long> {

    List<AccumulationPackageAttachedPLT> findByAccumulationPackageId(long id);
}
