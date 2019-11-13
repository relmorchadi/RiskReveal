package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ProjectConfigurationForeWriterFiles", schema = "dr")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectConfigurationForeWriterFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectConfigurationForeWriterFilesId")
    private Integer projectConfigurationForeWriterFilesId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "ProjectConfigurationForeWriterId")
    private Integer projectConfigurationForeWriterId;

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
