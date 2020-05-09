package com.scor.rr.repository.PLTManager;

import com.scor.rr.domain.entities.PLTManager.PLTManagerAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PLTManagerAllRepository extends JpaRepository<PLTManagerAll, Long>, JpaSpecificationExecutor<PLTManagerAll> {
}
