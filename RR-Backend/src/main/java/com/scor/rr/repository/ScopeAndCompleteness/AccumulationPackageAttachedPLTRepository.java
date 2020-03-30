package com.scor.rr.repository.ScopeAndCompleteness;

import com.scor.rr.domain.Response.ScopeAndCompleteness.PopUpPLTsResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageAttachedPLT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface AccumulationPackageAttachedPLTRepository extends JpaRepository<AccumulationPackageAttachedPLT,Long> {

    List<AccumulationPackageAttachedPLT> findByAccumulationPackageId(long id);

    @Transactional
    @Modifying
    @Query(value = "exec [dbo].[ExpectedScope_Get_PLTs_For_PopUp] @accumulationPackageId=:accumulationPackageId,@projectId=:projectId", nativeQuery = true)
    List<Map<String,Object>> getPLTsData(@Param("accumulationPackageId") long accumulationPackageId,@Param("projectId") long projectId);
}
