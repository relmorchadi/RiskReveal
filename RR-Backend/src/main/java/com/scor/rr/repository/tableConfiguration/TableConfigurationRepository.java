package com.scor.rr.repository.tableConfiguration;

import com.scor.rr.domain.entities.tableConfiguration.TableConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableConfigurationRepository extends JpaRepository<TableConfiguration, Integer> {
    Boolean existsByTableContextEqualsAndTableNameEquals(String tableContext, String tableName);
}
