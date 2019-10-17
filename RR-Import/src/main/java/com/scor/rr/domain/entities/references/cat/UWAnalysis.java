package com.scor.rr.domain.entities.references.cat;

import com.scor.rr.domain.entities.omega.Contract;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the UWAnalysis database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "UWAnalysis")
@Data
public class UWAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractId")
    private Contract contract;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "UWAnalysis_Divisions", joinColumns = {
            @JoinColumn(name = "UWAnalysisId")}, inverseJoinColumns = {@JoinColumn(name = "DivisionId")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Division> divisions;
}
