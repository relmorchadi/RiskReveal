package com.scor.rr.repository.userPreferences;

import com.scor.rr.domain.entities.userPreferences.TablePreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TablePreferencesRepository extends JpaRepository<TablePreferences, Integer> {
    Optional<TablePreferences> findByUIPageAndAndTableName(String uIPage, String tableName);
}
