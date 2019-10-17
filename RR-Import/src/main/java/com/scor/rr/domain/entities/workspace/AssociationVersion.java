package com.scor.rr.domain.entities.workspace;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the AssociationVersion database table
 *
 * @author HADDINI Zakariyae  && HAMIANI Mohammed
 */
@Entity
@Table(name = "AssociationVersion")
@Data
public class AssociationVersion {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssociationVersionId")
    private String associationVersionId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Tags")
    private String tags;
    @Column(name = "Notes")
    private String notes;
    @Column(name = "Version")
    private Integer version;
    @Column(name = "CreationDate")
    private Date creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectId")
    private Project project;

}
