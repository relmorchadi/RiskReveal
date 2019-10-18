package com.scor.rr.domain.entities.rms;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the ProjectImportAssetLog database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ProjectImportAssetLog")
@Data
public class ProjectImportAssetLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ProjectImportAssetLogId")
    private String projectImportAssetLogId;
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "ProjectImportLogId")
    private String projectImportLogId;
    @Column(name = "StepId")
    private Integer stepId;
    @Column(name = "StepName")
    private String stepName;
    @Column(name = "StartDate")
    private Date startDate;
    @Column(name = "EndDate")
    private Date endDate;
    @Transient
    private List<String> errorMessages;

    public List<String> getErrorMessages() {
        if (errorMessages == null) {
            errorMessages = new ArrayList<>();
        }
        return errorMessages;
    }

}
