package com.scor.rr.domain;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "PublicationPricingForeWriter")
@Data
public class PublicationPricingForeWriterEntity {

    
    @Id
    @Column(name = "PublicationPricingForeWriterEntityId", nullable = false)
    private Long publicationPricingForeWriterEntityId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;


}
