package com.scor.rr.domain.entities.plt;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ModellingSystemVersion database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModellingSystemVersion")
@Data
public class ModellingSystemVersion {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "ModellingSystemVersion")
    private Integer modellingSystemVersion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemId")
    private ModellingSystem modellingSystem;
}
