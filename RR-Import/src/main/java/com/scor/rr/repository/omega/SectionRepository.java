package com.scor.rr.repository.omega;

import com.scor.rr.domain.entities.omega.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Section Repository
 * 
 * @author HADDINI Zakariyae
 *
 */
public interface SectionRepository extends JpaRepository<Section, String> {

	@Query(value = "select s from Section s where s.contract.contractId = :contractId")
	Section findByContractId(@Param("contractId") String contractId);

}