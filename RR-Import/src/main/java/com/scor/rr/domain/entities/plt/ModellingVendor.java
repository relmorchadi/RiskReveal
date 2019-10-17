package com.scor.rr.domain.entities.plt;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ModellingVendor database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModellingVendor")
@Data
public class ModellingVendor {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
}
