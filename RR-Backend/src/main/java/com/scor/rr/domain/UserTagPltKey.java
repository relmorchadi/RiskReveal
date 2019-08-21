package com.scor.rr.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
class UserTagPltKey implements Serializable {


    @Column(name = "tagId")
    Integer tagId;

    @Column(name = "pltId")
    String pltId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTagPltKey that = (UserTagPltKey) o;
        return getTagId().equals(that.getTagId()) &&
                getPltId().equals(that.getPltId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagId(), getPltId());
    }
}
