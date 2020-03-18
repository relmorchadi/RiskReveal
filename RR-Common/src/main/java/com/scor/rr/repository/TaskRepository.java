package com.scor.rr.repository;

import com.scor.rr.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository  extends JpaRepository<TaskEntity, Long> {
}
