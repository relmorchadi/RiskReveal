package com.scor.rr.repository;

import com.scor.rr.domain.UserRPEntity;
import com.scor.rr.domain.UserRrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRPRepository extends JpaRepository<UserRPEntity, Long> {
}
