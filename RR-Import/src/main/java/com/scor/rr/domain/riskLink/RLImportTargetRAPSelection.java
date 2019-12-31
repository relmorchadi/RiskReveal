package com.scor.rr.domain.riskLink;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "RLImportTargetRAPSelection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RLImportTargetRAPSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RLImportTargetRAPSelectionId;
    @Column(name = "Entity")
    private Integer Entity;
    @Column(name = "TargetRAPCode")
    private String targetRAPCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RLImportSelectionId")
    private RLImportSelection rlImportSelection;

    public RLImportTargetRAPSelection(String code, RLImportSelection rlImportSelection) {
        this.rlImportSelection = rlImportSelection;
        this.targetRAPCode = code;
    }
}
