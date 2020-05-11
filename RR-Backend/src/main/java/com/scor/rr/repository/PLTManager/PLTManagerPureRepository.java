package com.scor.rr.repository.PLTManager;

import com.scor.rr.domain.entities.PLTManager.PLTManagerPure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PLTManagerPureRepository extends JpaRepository<PLTManagerPure, Long>, JpaSpecificationExecutor<PLTManagerPure> {
}
