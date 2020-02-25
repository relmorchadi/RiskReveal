package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RefInsureds")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RefInsureds {

    @Id()
    @Column( name = "InsuredId")
    private String insuredId;

    @Column( name = "Entity")
    private Long entity;

    @Column( name = "InsuredCode")
    private String insuredCode;

    @Column( name = "InsuredName")
    private String insuredName;

    @Column( name = "SecondName")
    private String secondName;

    @Column( name = "ClientCode")
    private String clientCode;

    @Column( name = "IsActive")
    private Boolean isActive;

}
