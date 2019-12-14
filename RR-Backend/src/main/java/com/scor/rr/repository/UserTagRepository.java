package com.scor.rr.repository;

import com.scor.rr.domain.entities.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    Optional<UserTag> findByTagIdAndUser(Long tagId, Integer userId);
}
