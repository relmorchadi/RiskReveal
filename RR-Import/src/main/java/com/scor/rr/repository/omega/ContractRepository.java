package com.scor.rr.repository.omega;

import com.scor.rr.domain.entities.omega.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contract Repository
 *
 * @author HADDINI Zakariyae
 */
public interface ContractRepository extends JpaRepository<Contract, String> {

    Contract findFirstByTreatyIdAndUwYearAndUwOrderAndEndorsementNumber(String treatyId, Integer uwYear, Integer uwOrder, Integer endorsementNumber);

    Contract findByTreatyIdAndUwYear(String treatyId, Integer uwYear);

}
