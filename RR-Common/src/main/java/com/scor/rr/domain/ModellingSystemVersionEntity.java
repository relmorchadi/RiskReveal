package com.scor.rr.domain;

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
public class ModellingSystemVersionEntity {
    @Id
    @Column(name = "Id")
    private String id;
    @Column(name = "ModellingSystemVersion")
    private Integer modellingSystemVersion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ModellingSystemId")
    private ModellingSystemEntity modellingSystem;
}
