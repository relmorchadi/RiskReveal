package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTagPlt {

    @EmbeddedId
    UserTagPltKey id;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tagId")
    UserTag tag;

    @ManyToOne
    @MapsId("pltId")
    @JoinColumn(name = "pltId")
    PltManagerView plt;

    @ManyToOne
    @JoinColumn(name = "AssignedBy")
    User assigner;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date AssignedAt;
}
