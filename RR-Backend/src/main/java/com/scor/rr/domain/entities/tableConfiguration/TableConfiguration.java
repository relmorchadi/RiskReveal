package com.scor.rr.domain.entities.tableConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TableConfiguration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TableConfigurationId")
    Integer tableConfigurationId;

    @Column(name = "DefaultColumns")
    String defaultColumns;

    @Column(name = "TableContext")
    String tableContext;

    @Column(name = "TableName")
    String tableName;

}
