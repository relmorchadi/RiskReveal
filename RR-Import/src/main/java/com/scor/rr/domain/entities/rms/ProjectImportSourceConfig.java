package com.scor.rr.domain.entities.rms;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the ProjectImportSourceConfig database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ProjectImportSourceConfig")
@Data
public class ProjectImportSourceConfig {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ProjectImportSourceConfigId")
    private String projectImportSourceConfigId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "SourceConfigVendor")
    private String sourceConfigVendor;
}
