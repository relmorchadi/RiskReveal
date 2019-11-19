package com.scor.rr.domain.TargetBuild.AccumulationPackage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AccumulationPackageAttachedPLT", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccumulationPackageAttachedPLT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccumulationPackageAttachedPLTid")
    private Long accumulationPackageAttachedPLTId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "AccumulationPackageId")
    private Long accumulationPackageId;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

    @Column(name = "ContractSectionId")
    private String contractSectionId;

}
