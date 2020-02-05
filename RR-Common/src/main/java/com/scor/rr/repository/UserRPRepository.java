package com.scor.rr.repository;

import com.scor.rr.domain.UserRPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRPRepository extends JpaRepository<UserRPEntity, Long> {
    Optional<UserRPEntity> findByUserIdAndRpAndScreen(Long userId, Integer rp, String screen);

    @Transactional
    void deleteByUserIdAndRpAndScreen(Long userId, Integer rp, String screen);

}
