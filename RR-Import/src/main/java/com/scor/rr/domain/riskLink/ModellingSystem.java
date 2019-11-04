package com.scor.rr.domain.riskLink;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModellingSystem database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModellingSystem")
@Data
public class ModellingSystem {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Name")
    private String name;
    @Column(name = "CatObjectId")
    private Integer catObjectId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VendorId")
    private ModellingVendor vendor;
}
