package com.scor.rr.domain.entities.references.cat;

import com.scor.rr.domain.entities.references.Coverage;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.references.omega.LineOfBusiness;
import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the Division database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Division")
@Data
public class Division {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Number")
    private Integer number;
    @Column(name = "PrincipalDivision")
    private Boolean principalDivision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrencyId")
    private Currency currency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CoverageId")
    private Coverage coverage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOBId")
    private LineOfBusiness lob;
}
