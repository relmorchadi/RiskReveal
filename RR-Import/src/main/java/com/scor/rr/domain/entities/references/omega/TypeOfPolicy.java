package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TypeOfPolicy database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "TypeOfPolicy")
@Data
public class TypeOfPolicy {
    @Id
    @Column(name = "TOPId")
    private Long topId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Label")
    private String label;
}
