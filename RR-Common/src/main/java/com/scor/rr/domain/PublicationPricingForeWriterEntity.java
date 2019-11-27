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
    @Column(name = "FWPublicationId", nullable = false)
    private Long publicationPricingForeWriterEntityId;
    @Basic
    @Column(name = "PLTHeaderId")
    private Long pltHeaderId;


}
