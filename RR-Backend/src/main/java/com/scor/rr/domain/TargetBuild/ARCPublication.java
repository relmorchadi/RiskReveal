package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ARCPublication")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ARCPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARCPublicationId")
    private Long arcPublicationId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

    @Column(name = "PLTLossDataFilePathHadoop")
    private String pltLossDataFilePathHadoop;

    @Column(name = "PLTLossDataFileNameHadoop")
    private String pltLossDataFileNameHadoop;

}
