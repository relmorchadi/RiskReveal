package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_PEQTRegionPerilMapping")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PEQTRegionPerilMapping {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "rrRegionPerilCode")
    private String rrRegionPerilCode;

    @Column(name = "peqtRegionPerilCode")
    private String peqtRegionPerilCode;
}
