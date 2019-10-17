package com.scor.rr.domain.entities.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the TreatySectionStatus database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "TreatySectionStatus")
@Data
public class TreatySectionStatus {
    @Id
    @Column(name = "TreatySectionStatusId")
    private Long treatySectionStatusId;
    @Column(name = "Code")
    private String code;
    @Column(name = "Label")
    private String label;
}
