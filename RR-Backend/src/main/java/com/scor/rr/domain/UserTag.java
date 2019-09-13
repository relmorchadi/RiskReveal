package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "RRUserTag", schema = "poc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @Column(unique = true)
    private String tagName;

    private String tagColor;

    @OneToMany(mappedBy = "userTagPltPk.tag",fetch = FetchType.LAZY)
    @JsonIgnore
    Set<UserTagPlt> assignment = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    Workspace workspace;

    //@JsonManagedReference
    @ManyToOne
    private User user;

    public UserTag(String tagName) {
        this.tagName = tagName;
    }

    public UserTag(String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public Integer getCount(){
        return this.assignment.size();
    }

    @Override
    public String toString() {
        return "UserTag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagColor='" + tagColor + '\'' +
                ", workspace=" + workspace +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTag)) return false;
        UserTag userTag = (UserTag) o;
        return tagId.equals(userTag.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId);
    }
}
