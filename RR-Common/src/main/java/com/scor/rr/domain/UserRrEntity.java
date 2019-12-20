package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "UserRR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRrEntity {

    @Id
    @Column(name = "UserId")
    private int userId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "FirstName", length = 15)
    private String firstName;

    @Column(name = "LastName", length = 15)
    private String lastName;

    @Column(name = "UserCode", length = 15)
    private String userCode;

    @Column(name = "omegaUser", length = 15)
    private String omegaUser;

    @Column(name = "WindowsUser", length = 15)
    private String windowsUser;

}
