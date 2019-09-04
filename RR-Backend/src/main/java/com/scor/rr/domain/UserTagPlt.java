package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_tag_plt"
)
public class UserTagPlt implements Serializable {

    @Id
    UserTagPltKey userTagPltPk;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "AssignedBy")
    User assigner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date assignedAt;

    private Integer uwYear;

    private String workSpaceId;

    public UserTagPlt(UserTag tag, PltHeader plt) {
        super();
        this.userTagPltPk = new UserTagPltKey(tag,plt);
    }

    public UserTag getTag() {
        return this.userTagPltPk.getTag();
    }

    public PltHeader getPlt() {
        return this.userTagPltPk.getPlt();
    }

    public void setTag(UserTag tag) {
        this.userTagPltPk.setTag(tag);
    }

    public void setPlt(PltHeader plt) {
        this.userTagPltPk.setPlt(plt);
    }


}
