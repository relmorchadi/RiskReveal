package com.scor.rr.domain.entities.references.cat;

import com.scor.rr.domain.entities.references.User;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the Narrative database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Narrative")
@Data
public class Narrative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NarrativeId")
    private Long narrativeId;
    @Column(name = "Description")
    private String description;
    @Column(name = "LastUpdateDate")
    private Date lastUpdateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LastUpdatedBy")
    private User lastUpdatedBy;
}
