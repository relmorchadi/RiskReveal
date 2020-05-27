package com.scor.rr.domain.entities.tableConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "UserTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserTableId")
    Integer userTableId;

    @Column(name = "UserColumns")
    String userColumns;

    @Column(name = "TableConfigurationId")
    Integer tableConfiguration;

    @Column(name = "VersionId")
    Integer versionId;

    @Column(name = "UserId")
    Long userId;

}
