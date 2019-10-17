package com.scor.rr.domain.entities.workspace;

import com.scor.rr.domain.entities.workspace.workspaceContext.WorkspaceContext;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the Workspace database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Workspace")
@Data
public class Workspace {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkspaceId")
    private String workspaceId;
    @Column(name = "CedantName")
    private String cedantName;
    @Column(name = "WorkspaceName")
    private String workspaceName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceContextId")
    private WorkspaceContext workspaceContext;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Workspace_Projects", joinColumns = {@JoinColumn(name = "WorkspaceId")}, inverseJoinColumns = {
            @JoinColumn(name = "ProjectId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Project> projects;

    public Workspace() {
    }
}
