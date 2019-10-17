package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the OutputPerspective database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "OutputPerspective")
@Data
public class OutputPerspective {
    @Id
    @Column(name = "OutputPerspectiveId")
    private Long outputPerspectiveId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String desc;
}
