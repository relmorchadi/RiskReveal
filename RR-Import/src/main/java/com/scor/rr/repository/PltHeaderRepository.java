package com.scor.rr.repository;

import com.scor.rr.domain.PLTHeader;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PltHeaderRepository extends JpaRepository<PLTHeader, Long> {
}
