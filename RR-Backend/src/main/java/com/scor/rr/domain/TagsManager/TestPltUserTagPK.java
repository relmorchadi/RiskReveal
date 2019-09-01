package com.scor.rr.domain.TagsManager;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TestPltUserTagPK implements Serializable {
    private String pltId;
    private int userTagId;

    @Column(name = "PltId")
    @Id
    public String getPltId() {
        return pltId;
    }

    public void setPltId(String pltId) {
        this.pltId = pltId;
    }

    @Column(name = "UserTagId")
    @Id
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
