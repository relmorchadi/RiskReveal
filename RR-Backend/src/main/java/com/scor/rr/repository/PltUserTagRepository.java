package com.scor.rr.repository;

import com.scor.rr.domain.PltHeader;
import com.scor.rr.domain.User;
import com.scor.rr.domain.UserTag;
import com.scor.rr.domain.UserTagPlt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PltUserTagRepository extends JpaRepository<UserTagPlt, Integer> {
    List<UserTagPlt> findUserTagPltsByAssigner(User assigner);

   // void deleteByUserTagPltPkTagAndPlt(UserTag tag, PltHeader plt);

    List<UserTagPlt> findTop10ByAssignerOrderByAssignedAtDesc(User assigner);

    @Query("from UserTagPlt where workSpaceId <> ?1 and uwYear <> ?2")
    List<UserTagPlt> findByNotInWS(String wsId, Integer uwYear);

    @Query("FROM UserTagPlt WHERE userTagPltPk.tag =:tag AND userTagPltPk.plt =:plt")
    UserTagPlt findByTagAndPlt(@Param("tag") UserTag tag, @Param("plt") PltHeader plt);

    List<UserTagPlt> findByAssignerAndWorkSpaceIdAndUwYear(User assigner, String workspaceId,Integer uwYear);
}
