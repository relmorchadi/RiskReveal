package com.scor.rr.repository.PLTManager;

import com.scor.rr.domain.entities.PLTManager.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    Optional<UserTag> findByTagIdAndUser(Long tagId, Long userId);
}
