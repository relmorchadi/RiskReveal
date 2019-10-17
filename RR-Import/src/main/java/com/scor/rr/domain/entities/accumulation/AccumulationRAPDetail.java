package com.scor.rr.domain.entities.accumulation;

import com.scor.rr.domain.entities.references.TargetRAP;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the AccumulationRAPDetail database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AccumulationRAPDetail")
@Data
public class AccumulationRAPDetail {
    @Id
    @Column(name = "AccumulationRAPDetailId")
    private Long accumulationRAPDetailId;
    @OneToOne
    @JoinColumn(name = "AccumulationRAPId")
    private AccumulationRAP accumulationRAP;

    @OneToMany
    @JoinColumn(name = "rapId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TargetRAP> targetRAP;
}
