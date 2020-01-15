package com.scor.rr.repository;

import com.scor.rr.domain.UserRPEntity;
import com.scor.rr.domain.UserRrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRPRepository extends JpaRepository<UserRPEntity, Long> {
    Optional<UserRPEntity> findByUserIdAndRp(Long userId, Integer rp);

    @Transactional
    void deleteByUserIdAndRp(Long userId, Integer rp);

}
