package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the LinkedLayerHandling database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "LinkedLayerHandling")
@Data
public class LinkedLayerHandling {
    @Id
    @Column(name = "LinkedLayerHandlingId")
    private Long linkedLayerHandlingId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Description")
    private String desc;
}
