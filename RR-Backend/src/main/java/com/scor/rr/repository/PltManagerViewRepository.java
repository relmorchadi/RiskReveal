package com.scor.rr.repository;

import com.scor.rr.domain.PltManagerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PltManagerViewRepository extends JpaRepository<PltManagerView, String>, JpaSpecificationExecutor<PltManagerView> {

}
