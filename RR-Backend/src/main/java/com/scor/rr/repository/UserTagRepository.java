package com.scor.rr.repository;

import com.scor.rr.domain.User;
import com.scor.rr.domain.UserTag;
import com.scor.rr.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserTagRepository extends JpaRepository<UserTag, Integer>{
    List<UserTag> findByUser(User user);
    List<UserTag> findByWorkspace(Workspace workspace);

    Optional<UserTag> findByTagName(String tagName);
    List<UserTag> findByTagIdIn(List<Integer> listIds);

    @Query("from UserTag tag where tag not in (SELECT userTagPltPk.tag from UserTagPlt)")
    List<UserTag> findAllUserTags();

    List<UserTag> findAllByWorkspaceNot(Workspace workspace);

    /*@Query("select distinct userTagPltPk.tag from UserTagPlt where assigner = ?1 and workSpaceId = ?2 and uwYear = ?3")
    List<UserTag> findByAssignerAndWorkSpaceIdAndUwYear(User user, String workspaceId,Integer uwYear);*/
}
