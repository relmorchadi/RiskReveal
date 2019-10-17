package com.scor.rr.domain.entities.workspace.workspaceContext;

import com.scor.rr.domain.entities.omega.Contract;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the WorkspaceContext database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "WorkspaceContext")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class WorkspaceContext {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "WorkspaceContextId", updatable = false, nullable = false)
    protected Long workspaceContextId;
    @Column(name = "WorkspaceContextCode")
    protected String workspaceContextCode;
    @Column(name = "WorkspaceUwYear")
    protected Integer workspaceUwYear;
    @OneToMany(mappedBy = "workspaceContext")
    private List<Contract> contracts;
    // @ManyToMany
    // @JoinTable(name = "Workspace_WorkspaceContext", joinColumns = {
    // @JoinColumn(name = "WorkspaceContextId", referencedColumnName =
    // "WorkspaceContextId") }, inverseJoinColumns = {
    // @JoinColumn(name = "WorkspaceId", referencedColumnName = "Workspace_Id")
    // })
    // private List<Workspace> workspaces;
}
