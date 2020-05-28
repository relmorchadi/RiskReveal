package com.scor.rr.repository.tableConfiguration;

import com.scor.rr.domain.entities.tableConfiguration.UserTablePreferencesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTablePreferencesViewRepo extends JpaRepository<UserTablePreferencesView, Long> {
    UserTablePreferencesView findByUserIdAndTableContextAndTableName(Long userId, String tableContext, String tableName);
}
