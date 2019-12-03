package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PublicationPricingxAct")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationPricingxAct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PublicationPricingxActId")
    private Long publicationPricingxActId;

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
