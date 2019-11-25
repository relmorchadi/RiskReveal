package com.scor.rr.repository;

import com.scor.rr.entity.RefFMFContractAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefFMFContractAttributeRepository extends JpaRepository<RefFMFContractAttribute, Long> {

    @Query(value="from RefFMFContractAttribute \n" +
            "where contractAttributeId in \n" +
            "(select contractAttributeId from RefFMFContractTypeAttributeMap\n" +
            " where contractTypeId = (SELECT contractTypeId FROM RefFMFContractType WHERE contractTypeCode = :code))")
    List<RefFMFContractAttribute> getAttributesForContract(@Param("code") String code);
}
