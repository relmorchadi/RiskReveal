package com.scor.rr.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "RRUser", schema = "poc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(unique = true)
    private String userName;

    @OneToMany(mappedBy = "user")
    private List<UserTag> userTags;

    @OneToMany(mappedBy = "assigner")
    private List<UserTagPlt> assignments;

    public User(String userName) {
        this.userName= userName;
    }

}
