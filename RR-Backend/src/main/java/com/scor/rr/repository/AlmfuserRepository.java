package com.scor.rr.repository;

import com.scor.rr.domain.AlmfUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlmfuserRepository extends JpaRepository<AlmfUserEntity, Integer> {
}