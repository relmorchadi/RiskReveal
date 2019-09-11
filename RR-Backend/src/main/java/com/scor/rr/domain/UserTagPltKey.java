package com.scor.rr.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
class UserTagPltKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "fk_tag", insertable = false, updatable = false)
    UserTag tag;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "fk_plt", insertable = false, updatable = false)
    PltHeader plt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTagPltKey)) return false;
        UserTagPltKey that = (UserTagPltKey) o;
        return tag.equals(that.tag) &&
                plt.equals(that.plt);
    }


    @Override
    public int hashCode() {
        return Objects.hash(tag, plt);
    }
}
