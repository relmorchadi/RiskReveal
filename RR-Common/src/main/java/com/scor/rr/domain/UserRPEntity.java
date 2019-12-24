package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ZZ_UserRP")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRPEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userRPId")
    private Integer userRPId;

    @Column(name = "UserId")
    private Integer userId;

    @Column(name = "rp")
    private Integer rp;

}
