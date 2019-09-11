package com.scor.rr.repository;

import com.scor.rr.domain.ContractSearchResult;
import com.scor.rr.domain.WorkspaceProjection;
import com.scor.rr.domain.dto.NewWorkspaceFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;


public interface ContractSearchResultRepository extends JpaRepository<ContractSearchResult, String>, JpaSpecificationExecutor<ContractSearchResult> {

    Stream<ContractSearchResult> findDistinctByWorkSpaceId(String uwy);

    @Query("select c from ContractSearchResult c where c.workSpaceId= :wkspId and concat(c.uwYear,'')= :uwy order by c.workSpaceId asc, c.uwYear asc")
    List<ContractSearchResult> findByTreatyidAndUwYear(@Param("wkspId") String workspaceId, String uwy);


    @Query(value = "select c.workSpaceId as workSpaceId, c.workspaceName as workspaceName, c.uwYear as uwYear, c.cedantCode as cedantCode, c.cedantName as cedantName, c.countryName as countryName from ContractSearchResult c " +
            " where c.workSpaceId like :kw or c.workspaceName like :kw or c.uwYear like :kw or c.cedantCode like :kw or c.cedantName like :kw or c.countryName like :kw" +
            " GROUP BY  c.workSpaceId,c.workspaceName, c.uwYear, c.cedantCode, c.cedantName, c.countryName")
    Page<WorkspaceProjection> globalSearch(@Param("kw") String keyword, Pageable pageable);

    @Query(value = "EXEC filterContracts :offset,:size, :#{#f.workspaceId}, :#{#f.innerWorkspaceId}, :#{#f.workspaceName}, :#{#f.innerWorkspaceName}, :#{#f.year}, :#{#f.innerYear}, :#{#f.cedantCode}, :#{#f.innerCedantCode}, :#{#f.cedantName}, :#{#f.innerCedantName}, :#{#f.countryName}, :#{#f.innerCountryName}", nativeQuery = true)
    List<WorkspaceProjection> getContracts(@Param("f") NewWorkspaceFilter f, @Param("offset") int offset, @Param("size") int size);

    @Query(value = "EXEC countFilterContracts :#{#f.workspaceId},:#{#f.innerWorkspaceId},:#{#f.workspaceName}, :#{#f.innerWorkspaceName},:#{#f.year},:#{#f.innerYear}, :#{#f.cedantCode},:#{#f.innerCedantCode}, :#{#f.cedantName},:#{#f.innerCedantName}, :#{#f.countryName}, :#{#f.innerCountryName}", nativeQuery = true)
    long countContracts(@Param("f") NewWorkspaceFilter f);

}
