package com.scor.rr.repository.tableConfiguration;

import com.scor.rr.domain.entities.tableConfiguration.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTableRepo extends JpaRepository<UserTable, Integer> {
    UserTable findByUserId(Long userId);
}
