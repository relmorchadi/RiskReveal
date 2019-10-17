package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.references.omega.Subsidiary;
import com.scor.rr.domain.entities.references.omega.SubsidiaryLedger;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the UWUnit database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "UWUnit")
@Data
public class UWUnit {

    @Id
    @Column(name = "UWUnitId")
    private String uwUnitId;
    @Column(name = "Identifier")
    private String identifier;
    @Column(name = "MarketType")
    private String marketType;
    @Column(name = "Name")
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubsidiaryId")
    private Subsidiary subsidiary;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubsidiaryLedgerId")
    private SubsidiaryLedger subsidiaryLedger;
}
