package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.enums.ImportStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the ImportDecision database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ImportDecision")
@Data
public class ImportDecision {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImportDecisionId")
    private String importDecisionId;
    @Column(name = "ImportEndDate")
    private Date importEndDate;
    @Column(name = "ImportStartDate")
    private Date importStartDate;
    @Column(name = "LossImportEndDate")
    private Date lossImportEndDate;
    @Column(name = "ImportSequence")
    private Integer importSequence;
    @Column(name = "ErrorMessages")
    private String errorMessages;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ImportedBy")
    private User importedBy;
    @Column(name = "ImportStatus")
    private ImportStatus importStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssociationVersionId")
    private AssociationVersion associationVersion;

}
