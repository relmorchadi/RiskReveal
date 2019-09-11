package com.scor.rr.repository;

import com.scor.rr.domain.UserRrEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRrRepository extends JpaRepository<UserRrEntity, String> {
}
