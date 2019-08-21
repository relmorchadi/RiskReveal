package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "RRUserTag", schema = "poc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;
    @Column(unique = true)
    private String tagName;
    private String tagColor;

    @OneToMany(mappedBy = "plt")
    Set<UserTagPlt> pltHeaders;

    @ManyToOne
    @JsonIgnore
    Workspace workspace;

    @ManyToOne
    private User user;

    public UserTag(String tagName) {
        this.tagName = tagName;
    }

    public UserTag(String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }
}
