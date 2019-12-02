package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.Tag;
import com.scor.rr.domain.TargetBuild.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    Optional<UserTag> findByTagIdAndUser(Long tagId, Integer userId);
}
