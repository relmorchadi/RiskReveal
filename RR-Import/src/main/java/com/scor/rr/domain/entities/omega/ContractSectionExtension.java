package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.workspace.Workspace;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the ContractSectionExtension database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractSectionExtension")
@Data
public class ContractSectionExtension {
    @Id
    @Column(name = "ContractSectionExtensionId")
    private Long contractSectionExtensionId;
    @Column(name = "Complete")
    private Boolean complete;
    @Column(name = "CompletedWorkSpace")
    private Boolean completedWorkSpace;
    @Column(name = "Published")
    private Boolean published;
    @Column(name = "PublishedDate")
    private Date publishedDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionId")
    private Section section;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceId")
    private Workspace workSpace;
}
