package com.scor.rr.domain.entities.references.omega;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the LineOfBusiness database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "LineOfBusiness")
@Data
public class LineOfBusiness {
    @Id
    @Column(name = "LOBId")
    private Long lobId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Label")
    private String label;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "LOBId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PeriodBasis> periodBasis;
}
