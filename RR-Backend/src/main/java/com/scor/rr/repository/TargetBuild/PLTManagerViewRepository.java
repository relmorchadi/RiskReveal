package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.PLTManagerView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
