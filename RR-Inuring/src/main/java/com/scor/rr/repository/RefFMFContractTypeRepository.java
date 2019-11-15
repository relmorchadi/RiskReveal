package com.scor.rr.repository;

import com.scor.rr.entity.RefFMFContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefFMFContractTypeRepository extends JpaRepository<RefFMFContractType,Long> {
    RefFMFContractType findByContractTypeId(long contractTypeId);
}
