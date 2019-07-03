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

    @ManyToMany(
    cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(
            name = "user_tag_plt",
            joinColumns = @JoinColumn(name = "tagId"),
            inverseJoinColumns = @JoinColumn(name = "_id"))
    Set<PltHeader> pltHeaders;

    @ManyToOne
    @JsonIgnore
    Workspace workspace;

    public UserTag(String tagName) {
        this.tagName = tagName;
    }

    public UserTag(String tagName, String tagColor) {
        this.tagName = tagName;
        this.tagColor = tagColor;
    }
}
