package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "PLTHeaderTag", schema = "tb")
@Data
@NoArgsConstructor
public class PLTHeaderTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLTHeaderTagId")
    private Integer pltHeaderTagId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "pltHeaderId")
    private Integer pltHeaderId;

    @Column(name = "WorkspaceId")
    private Integer workspaceId;

    @Column(name = "TagId")
    private Integer tagId;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @CreatedDate
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private Date createdDate;

    public PLTHeaderTag(Integer pltHeaderId, Integer tagId) {
        this.pltHeaderId = pltHeaderId;
        this.tagId = tagId;
        this.entity = 1;
    }
}
