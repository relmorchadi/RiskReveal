package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.PLTHeaderTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface PLTHeaderTagRepository extends JpaRepository<PLTHeaderTag, Long> {
    Optional<PLTHeaderTag> findByPltHeaderIdAndTagId(Long pltHeaderId, Long tagId);

    Set<PLTHeaderTag> findByPltHeaderId(Long pltHeaderId);

    Set<PLTHeaderTag> findByWorkspaceId(Long workspaceId);

}
