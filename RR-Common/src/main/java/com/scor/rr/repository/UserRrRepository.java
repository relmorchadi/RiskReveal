package com.scor.rr.repository;

import com.scor.rr.domain.UserRrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRrRepository extends JpaRepository<UserRrEntity, Long> {

    @Query("select c from UserRrEntity c where c.userId= :userId")
    UserRrEntity findByUserName(int userId);

    UserRrEntity findByWindowsUser(String windowsUser);

    Optional<UserRrEntity> findByUserCode(String code);

    UserRrEntity findByUserId(long userId);
}
