package com.scor.rr.repository;

import com.scor.rr.domain.entities.PLTManagerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PLTManagerViewRepository extends JpaRepository<PLTManagerView, Integer> {
    /*@Query("SELECT " +
            "pltManagerView.pltId," +
            "pltManagerView.pltName," +
            "pltManagerView.archived," +
            "pltManagerView.archivedDate," +
            "(SELECT COUNT(pltHeaderTagForCount.pltHeaderId) from PLTHeaderTag AS pltHeaderTagForCount INNER JOIN Tag tagForCount WHERE pltHeaderView.uwYear = :uwYear AND pltHeaderView.workspaceContextCode = :workspaceContextCode GROUP BY tagForCount) AS count " +
            "FROM PLTManagerView AS pltManagerView " +
            "LEFT JOIN PLTHeaderTag AS pltHeaderTag ON pltHeaderTag.pltHeaderId = pltManagerView.pltId " +
            "LEFT JOIN Tag AS tag ON tag.tagId = pltHeaderTag.tagId " +
            "WHERE pltHeaderView.uwYear = :uwYear AND pltHeaderView.workspaceContextCode = :workspaceContextCode " +
            "GROUP BY pltManagerView.pltId ")*/
    Set<PLTManagerView> findByWorkspaceContextCodeAndUwYear(@Param("workspaceContextCode") String workspaceContextCode,@Param("uwYear") Integer uwYear);

    @Query("FROM PLTManagerView plt WHERE plt.workspaceContextCode = :workspaceContextCode AND plt.uwYear = :uwYear AND plt.deletedOn is not null AND plt.deletedDue is not null AND plt.deletedBy is not null")
    Set<PLTManagerView> findDeletedPLTs(@Param("workspaceContextCode") String workspaceContextCode,@Param("uwYear") Integer uwYear);

    @Query("FROM PLTManagerView WHERE workspaceContextCode = :workspaceContextCode AND uwYear = :uwYear AND deletedOn is null AND deletedDue is null AND deletedBy is null")
    Set<PLTManagerView> findPLTs(@Param("workspaceContextCode") String workspaceContextCode,@Param("uwYear") Integer uwYear);


    @Query(value = "EXEC dbonew.usp_PLTManagerGetThreadEndPLTs " +
            "@WorkspaceContextCode= :WorkspaceContextCode, " +
            "@WorkspaceUwYear= :WorkspaceUwYear, " +
            "@Entity =:Entity, " +
            "@UserCode =:UserCode, " +
            "@PageNumber =:PageNumber, " +
            "@PageSize =:PageSize, " +
            "@SelectionList =:SelectionList, " +
            "@SortSelectedFirst =:SortSelectedFirst, " +
            "@SortSelectedAction =:SortSelectedAction", nativeQuery = true)
    List<Map<String, Object>> getPLTManagerData(
            @Param("WorkspaceContextCode") String WorkspaceContextCode,
            @Param("WorkspaceUwYear") Integer WorkspaceUwYear,
            @Param("Entity") Integer Entity,
            @Param("UserCode") String UserCode,
            @Param("PageNumber") Integer PageNumber,
            @Param("PageSize") Integer PageSize,
            @Param("SelectionList") String SelectionList,
            @Param("SortSelectedFirst") Boolean SortSelectedFirst,
            @Param("SortSelectedAction") String SortSelectedAction
            );


    @Query(value = "EXEC dbonew.usp_GetColumnsByUserCodeAndViewContext @UserCode =:UserCode, @ViewContext =:ViewContext", nativeQuery = true)
    List<Map<String, Object>> getColumns(@Param("UserCode") String userCode, @Param("ViewContext") Long viewContext);

    @Query(value = "EXEC dbonew.usp_PLTManagerGetThreadEndPLTsIDs " +
            "@WorkspaceContextCode= :WorkspaceContextCode, " +
            "@WorkspaceUwYear= :WorkspaceUwYear, " +
            "@Entity =:Entity, " +
            "@UserCode =:UserCode", nativeQuery = true)
    List<Map<String, Object>> getIDs(
            @Param("WorkspaceContextCode") String WorkspaceContextCode,
            @Param("WorkspaceUwYear") Integer WorkspaceUwYear,
            @Param("Entity") Integer Entity,
            @Param("UserCode") String UserCode
    );
}
