package com.scor.rr.domain.entities.references.omega;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the Client database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Client")
@Data
public class Client {
    @Id
    @Column(name = "ClientId")
    private String clientId;
    @Column(name = "ClientNumber")
    private String clientNumber;
    @Column(name = "ClientShortName")
    private String clientShortName;
    @Column(name = "ClientGroupCode")
    private String clientGroupCode;
    @Column(name = "ClientGroupName")
    private String clientGroupName;
    @Column(name = "Type")
    private String type;
    @Column(name = "Description")
    private String description;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CountryId")
    private Country country;
}
