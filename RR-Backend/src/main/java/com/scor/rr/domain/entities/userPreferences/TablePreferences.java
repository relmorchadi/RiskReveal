package com.scor.rr.domain.entities.userPreferences;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TablePreferences")
@Data
@NoArgsConstructor
public class TablePreferences {

    @Id
    private  Integer tablePreferencesId;
    private Integer entity;
    private  String uIPage;
    private  String tableName;
    private  String columns;
}
