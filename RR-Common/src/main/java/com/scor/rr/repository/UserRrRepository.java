package com.scor.rr.repository;

import com.scor.rr.domain.UserRrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRrRepository extends JpaRepository<UserRrEntity, String> {

    @Query("select c from UserRrEntity c where c.userId= :userName")
    UserRrEntity findByUserName(String userName);
}
