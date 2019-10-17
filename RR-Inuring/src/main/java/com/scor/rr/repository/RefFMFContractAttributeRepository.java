package com.scor.rr.repository;

import com.scor.rr.entity.RefFMFContractAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefFMFContractAttributeRepository extends JpaRepository<RefFMFContractAttribute, Integer> {
}
