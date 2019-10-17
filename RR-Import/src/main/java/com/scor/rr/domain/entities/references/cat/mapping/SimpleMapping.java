package com.scor.rr.domain.entities.references.cat.mapping;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the SimpleMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "SimpleMapping")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class SimpleMapping<T> extends AbstractMapping<T> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "SourceValueCode")
    private String sourceValueCode;
    @Column(name = "SourceValueDescription")
    private String sourceValueDescription;

}
