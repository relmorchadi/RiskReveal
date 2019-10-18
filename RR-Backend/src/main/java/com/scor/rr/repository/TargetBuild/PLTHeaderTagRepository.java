package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.PLTHeaderTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PLTHeaderTagRepository extends JpaRepository<PLTHeaderTag, Integer> {
    Optional<PLTHeaderTag> findByPltHeaderIdAndTagId(Integer pltHeaderId, Integer tagId);

    Set<PLTHeaderTag> findByPltHeaderId(Integer pltHeaderId);

    Set<PLTHeaderTag> findByWorkspaceId(Long workspaceId);

}
