package com.scor.rr.repository.PLTManager;

import com.scor.rr.domain.entities.PLTManager.PLTManagerThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PLTManagerThreadRepository extends JpaRepository<PLTManagerThread, Long>, JpaSpecificationExecutor<PLTManagerThread> {
}
