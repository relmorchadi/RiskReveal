package com.scor.rr.repository;

import com.scor.rr.domain.entities.CarContractView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarContractViewRepository extends JpaRepository<CarContractView, Long> {

    CarContractView findByProjectId(Long projectId);
}
