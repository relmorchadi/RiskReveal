package com.scor.rr.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //@JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserTag> userTags;

    @OneToMany(mappedBy = "assigner")
    @JsonIgnore
    private List<UserTagPlt> assignments;

    public User(String userName) {
        this.userName= userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
