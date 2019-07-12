package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ScorPLTHeaderTags", schema = "dbo", catalog = "RiskReveal")
public class ScorPltHeaderTagsEntity {
    private int id;
    private Integer scorPltHeaderId;
    private String tag;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "scorPLTHeaderId", nullable = true)
    public Integer getScorPltHeaderId() {
        return scorPltHeaderId;
    }

    public void setScorPltHeaderId(Integer scorPltHeaderId) {
        this.scorPltHeaderId = scorPltHeaderId;
    }

    @Basic
    @Column(name = "tag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScorPltHeaderTagsEntity that = (ScorPltHeaderTagsEntity) o;
        return id == that.id &&
                Objects.equals(scorPltHeaderId, that.scorPltHeaderId) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scorPltHeaderId, tag);
    }
}
