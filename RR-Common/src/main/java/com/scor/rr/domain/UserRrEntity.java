package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "UserRR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRrEntity {

    @Id
    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "userFirstName")
    private String firstName;

    @Column(name = "userLastName")
    private String lastName;

    @Column(name = "userRole")
    private String role;

    @Column(name = "UserCode", length = 15)
    private String userCode;

    @Column(name = "OmegaUser", length = 15)
    private String omegaUser;

    @Column(name = "WindowsUser", length = 15)
    private String windowsUser;

    @Transient @JsonIgnore
    private String jwtToken;

}
