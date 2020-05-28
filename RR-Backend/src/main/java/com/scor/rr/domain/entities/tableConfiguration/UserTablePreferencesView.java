package com.scor.rr.domain.entities.tableConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_UserTablePreferences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTablePreferencesView {

    @Id
    Long id;

    String columns;

    String tableName;

    String tableContext;

    Integer tableConfigurationId;
    Integer userTableId;

    Long userId;

}
