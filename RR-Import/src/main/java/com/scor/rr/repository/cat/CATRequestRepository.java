package com.scor.rr.repository.cat;

import com.scor.rr.domain.entities.cat.CATRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * CATRequest Repository
 *
 * @author HADDINI Zakariyae
 */
public interface CATRequestRepository extends JpaRepository<CATRequest, String> {

    @Query(value = "FROM CATRequest WHERE catRequestId=:id")
    CATRequest queryForStatus(@Param("id") String id);
}