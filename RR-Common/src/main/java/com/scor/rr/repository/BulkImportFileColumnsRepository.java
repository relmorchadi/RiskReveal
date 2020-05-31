package com.scor.rr.repository;

import com.scor.rr.domain.BulkImportFileColumns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BulkImportFileColumnsRepository extends JpaRepository<BulkImportFileColumns, Long> {

    @Query("SELECT b.columnName FROM BulkImportFileColumns b WHERE b.importance=:importance")
    List<String> findByImportance(@Param("importance") String importance);
}
