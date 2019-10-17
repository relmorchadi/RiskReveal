package com.scor.rr.repository.stat;

import com.scor.rr.domain.entities.stat.RRStatisticHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * RRStatisticHeader Repository
 *
 * @author HADDINI Zakariyae
 */
public interface RRStatisticHeaderRepository extends JpaRepository<RRStatisticHeader, String> {

    List<RRStatisticHeader> findByLossTableId(String lossTableId);

}
