package com.scor.rr.domain.entities.workspace;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the WorkspaceMinimumGrain database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "WorkspaceMinimumGrain")
@Data
public class WorkspaceMinimumGrain {
    @Id
    @Column(name = "Id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "WorkspaceId")
    private String workspaceId;
    @Column(name = "MinimumGrain")
    private String minimumGrain;
}
