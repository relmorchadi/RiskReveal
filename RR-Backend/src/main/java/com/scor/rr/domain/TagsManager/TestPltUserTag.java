package com.scor.rr.domain.TagsManager;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TmPltUserTag", schema = "PRD", catalog = "RR")
@IdClass(TestPltUserTagPK.class)
public class TestPltUserTag {
    private String pltId;
    private int userTagId;

    @Id
    @Column(name = "PltId")
    public String getPltId() {
        return pltId;
    }

    public void setPltId(String pltId) {
        this.pltId = pltId;
    }

    @Id
    @Column(name = "UserTagId")
    public int getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(int userTagId) {
        this.userTagId = userTagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pltId, userTagId);
    }
}
