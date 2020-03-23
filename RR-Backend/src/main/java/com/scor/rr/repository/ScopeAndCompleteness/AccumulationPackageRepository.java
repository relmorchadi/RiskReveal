package com.scor.rr.repository.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface AccumulationPackageRepository extends JpaRepository<AccumulationPackage,Long> {

    @Transactional
    @Modifying
    @Query(value = "exec [dbo].[usp_ExpectedScope_Get_ScopeOnly_By_FACNumber_UWYear] @facNumber=:facNumber,@uwYear=:uwYear", nativeQuery = true)
    List<Map<String,Object>> getExpectedScopeOnly(@Param("facNumber") String facNumber,@Param("uwYear") int uwYear);

    AccumulationPackage findByAccumulationPackageId(long id);
}
