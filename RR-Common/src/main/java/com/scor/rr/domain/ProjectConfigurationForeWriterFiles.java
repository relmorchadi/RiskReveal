package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ProjectConfigurationForeWriterFiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriterFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterFilesId")
    private Long projectConfigurationForeWriterFilesId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectConfigurationForeWriterId")
    private Long projectConfigurationForeWriterId;

    @Column(name = "AccFileName")
    private String accFileName;

    @Column(name = "AccFileId")
    private Integer accFileId;

    @Column(name = "LocFileName")
    private String locFileName;

    @Column(name = "LocFileId")
    private Integer locFileId;

    @Column(name = "vIsFileName")
    private String vIsFileName;

    @Column(name = "vIsFileId")
    private Integer vIsFileId;

}
