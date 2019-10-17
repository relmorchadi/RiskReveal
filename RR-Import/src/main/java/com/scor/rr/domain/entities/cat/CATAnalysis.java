package com.scor.rr.domain.entities.cat;

import com.scor.rr.domain.entities.references.cat.Narrative;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the CATAnalysis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "CATAnalysis")
@Data
public class CATAnalysis {

    public static final String DEFAULT = "Default";

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATAnalysisId")
    private String catAnalysisId;
    @Column(name = "Name")
    private String name;
    @Column(name = "IsDefault")
    private Boolean isDefault;
    @Column(name = "Description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NarrativeId")
    private Narrative narrative;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATRequestId")
    private CATRequest catRequest;

    public static String buildId(String carId) {
        return carId + "_" + DEFAULT;
    }
}
