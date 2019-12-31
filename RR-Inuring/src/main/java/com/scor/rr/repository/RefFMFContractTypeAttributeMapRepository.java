package com.scor.rr.repository;

import com.scor.rr.entity.RefFMFContractAttribute;
import com.scor.rr.entity.RefFMFContractTypeAttributeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefFMFContractTypeAttributeMapRepository extends JpaRepository<RefFMFContractTypeAttributeMap,Long> {

}
