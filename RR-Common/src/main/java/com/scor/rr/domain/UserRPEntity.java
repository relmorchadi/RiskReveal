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
    private Long userId;

    @Column(name = "rp")
    private Integer rp;


    public UserRPEntity(Integer rp, Long userId) {
        this.rp = rp;
        this.userId = userId;
    }
}
