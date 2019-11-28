package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PublicationARC")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationARC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PublicationARCId")
    private Long publicationARCId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

    @Column(name = "PLTLossDataFilePathHadoop")
    private String pltLossDataFilePathHadoop;

    @Column(name = "PLTLossDataFileNameHadoop")
    private String pltLossDataFileNameHadoop;

}
