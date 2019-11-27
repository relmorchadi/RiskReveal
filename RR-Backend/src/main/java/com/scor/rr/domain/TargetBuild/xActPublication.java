package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "xActPublication")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class xActPublication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "xActPublicationId")
    private Long xActPublicationId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;

    @Column(name = "xActAvailable")
    private Boolean xActAvailable;

    @Column(name = "xActUsed")
    private Boolean xActUsed;

    @Column(name = "xActPublicationDate")
    private Date xActPublicationDate;

    @Column(name = "PublishedBy")
    private String publishedBy;

}
