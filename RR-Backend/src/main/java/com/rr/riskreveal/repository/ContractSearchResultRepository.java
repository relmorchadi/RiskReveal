package com.rr.riskreveal.repository;

import com.rr.riskreveal.domain.ContractSearchResult;
import com.rr.riskreveal.domain.WorkspaceProjection;
import com.rr.riskreveal.domain.dto.NewWorkspaceFilter;
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

    @Query(value = "select c.workSpaceId as workSpaceId, c.workspaceName as workspaceName, c.uwYear as uwYear, c.cedantCode as cedantCode, c.cedantName as cedantName, c.countryName as countryName from ContractSearchResult c GROUP BY  WorkSpaceId,WorkspaceName, UwYear, CedantCode, CedantName, CountryName")
    Page<WorkspaceProjection> customQuery(Pageable pageable);

    @Query(value = "EXEC filterContracts :s, :#{#f.workspaceId}, :#{#f.innerWorkspaceId}, :#{#f.workspaceName}, :#{#f.innerWorkspaceName}, :#{#f.year}, :#{#f.innerYear}, :#{#f.cedantCode}, :#{#f.innerCedantCode}, :#{#f.cedantName}, :#{#f.innerCedantName}, :#{#f.countryName}, :#{#f.innerCountryName}", nativeQuery = true)
    List<WorkspaceProjection> getContracts(@Param("f") NewWorkspaceFilter f, @Param("s") int s);

    @Query(value = "EXEC countFilterContracts :#{#f.workspaceId},:#{#f.innerWorkspaceId},:#{#f.workspaceName}, :#{#f.innerWorkspaceName},:#{#f.year},:#{#f.innerYear}, :#{#f.cedantCode},:#{#f.innerCedantCode}, :#{#f.cedantName},:#{#f.innerCedantName}, :#{#f.countryName}, :#{#f.innerCountryName}", nativeQuery = true)
    long countContracts(@Param("f") NewWorkspaceFilter f);

}
