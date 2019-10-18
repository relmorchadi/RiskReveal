package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.entities.references.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the Project database table
 *
 * @author HADDINI Zakariyae  && HAMIANI Mohammed
 */
@Entity
@Table(name = "Project")
@Data
public class Project {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ProjectId")
    private String projectId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "MasterFlag")
    private Boolean masterFlag;
    @Column(name = "LinkFlag")
    private Boolean linkFlag;
    @Column(name = "PublishFlag")
    private Boolean publishFlag;
    @Column(name = "ClonedFlag")
    private Boolean clonedFlag;
    @Column(name = "PostInuredFlag")
    private Boolean postInuredFlag;
    @Column(name = "MGAFlag")
    private Boolean mgaFlag;
    @Column(name = "CreationDate")
    private Date creationDate;
    @Column(name = "ReceptionDate")
    private Date receptionDate;
    @Column(name = "DueDate")
    private Date dueDate;
    @Column(name = "Deleted")
    private Boolean deleted;
    @Column(name = "DeletedOn")
    private Date deletedOn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MGAId")
    private MGA mga;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssignedTo")
    private User assignedTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedBy")
    private User createdBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DeletedBy")
    private User deletedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SourceProjectId")
    private Project sourceProject;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "sourceProject")
//    @JoinTable(name = "Cloned_Projects", joinColumns = {@JoinColumn(name = "SourceProjectId")}, inverseJoinColumns = {
//            @JoinColumn(name = "ClonedProjectId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Project> clonedProjects;

    public Project() {
    }

    public Project(String projectId) {
        this.projectId = projectId;
    }
}
