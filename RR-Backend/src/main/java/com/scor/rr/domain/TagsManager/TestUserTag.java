package com.scor.rr.domain.TagsManager;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TmUserTag", schema = "PRD", catalog = "RR")
public class TestUserTag {
    private int userTagId;
    private String tagName;
    private String tagOwner;
    private int favouriteTag;

    @Id
    @Column(name = "UserTagId")
    public int getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(int userTagId) {
        this.userTagId = userTagId;
    }

    @Basic
    @Column(name = "TagName")
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Basic
    @Column(name = "TagOwner")
    public String getTagOwner() {
        return tagOwner;
    }

    public void setTagOwner(String tagOwner) {
        this.tagOwner = tagOwner;
    }

    @Basic
    @Column(name = "FavouriteTag")
    public int getFavouriteTag() {
        return favouriteTag;
    }

    public void setFavouriteTag(int favouriteTag) {
        this.favouriteTag = favouriteTag;
    }


    @Override
    public int hashCode() {
        return Objects.hash(userTagId, tagName, tagOwner, favouriteTag);
    }
}
