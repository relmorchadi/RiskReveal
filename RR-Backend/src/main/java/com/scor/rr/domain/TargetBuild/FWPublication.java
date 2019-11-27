package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "FWPublication")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FWPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FWPublicationId")
    private Long fwPublicationId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

}
