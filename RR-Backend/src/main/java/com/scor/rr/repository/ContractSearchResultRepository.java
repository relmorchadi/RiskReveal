package com.scor.rr.repository;

import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.WorkspaceProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public interface ContractSearchResultRepository extends JpaRepository<ContractSearchResult, String>, JpaSpecificationExecutor<ContractSearchResult> {


    @Query("select distinct c.uwYear from ContractSearchResult c where c.workSpaceId= :wsId order by c.uwYear asc")
    List<Integer> findDistinctYearsByWorkSpaceId(@Param("wsId") String wsId);

    @Query("select c from ContractSearchResult c where c.workSpaceId= :wkspId and concat(c.uwYear,'')= :uwy order by c.workSpaceId asc, c.uwYear asc")
    List<ContractSearchResult> findByTreatyidAndUwYear(@Param("wkspId") String workspaceId,@Param("uwy") String uwy);

    @Query("select c from ContractSearchResult c where c.workSpaceId= :wkspId and c.uwYear= :uwy order by c.workSpaceId asc, c.uwYear asc")
    Optional<ContractSearchResult> findByWorkspaceIdAndUwYear(@Param("wkspId") String workspaceId,@Param("uwy") Integer uwy);

    @Query(value = "select c.workSpaceId as workSpaceId, c.workspaceName as workspaceName, c.uwYear as uwYear, c.cedantCode as cedantCode, c.cedantName as cedantName, c.countryName as countryName from ContractSearchResult c " +
            " where c.workSpaceId like :kw or c.workspaceName like :kw or c.uwYear like :kw or c.cedantCode like :kw or c.cedantName like :kw or c.countryName like :kw" +
            " GROUP BY  c.workSpaceId,c.workspaceName, c.uwYear, c.cedantCode, c.cedantName, c.countryName")
    Page<WorkspaceProjection> globalSearch(@Param("kw") String keyword,Pageable pageable);

    /* @ TODO Implement the Count Search logic Queries */


}
