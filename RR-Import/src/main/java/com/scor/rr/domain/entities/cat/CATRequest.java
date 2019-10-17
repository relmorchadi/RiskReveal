package com.scor.rr.domain.entities.cat;

import com.scor.rr.domain.entities.references.User;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.references.cat.Narrative;
import com.scor.rr.domain.entities.references.cat.Status;
import com.scor.rr.domain.entities.references.cat.UWAnalysis;
import com.scor.rr.domain.enums.CARType;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the CATRequest database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CATRequest")
@Data
public class CATRequest {
    @Id
    @Column(name = "CATRequestId")
    private String catRequestId;
    @Column(name = "AssignDate")
    private Date assignDate;
    @Column(name = "AccFileNames")
    private String accFileNames;
    @Column(name = "LocFileNames")
    private String locFileNames;
    @Column(name = "VlsFileNames")
    private String vlsFileNames;
    @Column(name = "LastUpdateDate")
    private Date lastUpdateDate;
    @Column(name = "RequestCreationDate")
    private Date requestCreationDate;
    @Column(name = "Type")
    private CARType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusId")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssignedTo")
    private User assignedTo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RequestedBy")
    private User requestedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LastUpdatedBy")
    private User lastUpdatedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NarrativeId")
    private Narrative narrative;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UWAnalysisId")
    private UWAnalysis uwAnalysis;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemInstanceId")
    private ModellingSystemInstance modellingSystemInstance;
}
