package com.scor.rr.repository;

import com.scor.rr.domain.PltManagerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PltManagerViewRepository extends JpaRepository<PltManagerView, String>, JpaSpecificationExecutor<PltManagerView> {
}
