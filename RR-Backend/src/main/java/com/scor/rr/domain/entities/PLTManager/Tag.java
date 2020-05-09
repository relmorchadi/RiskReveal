package com.scor.rr.domain.entities.PLTManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagId")
    @EqualsAndHashCode.Include
    private Long tagId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "TagName")
    private String tagName;

    @Column(name = "CreatedBy")
    private Integer createdBy;

    @CreatedDate
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "DefaultColor", length = 8)
    private String defaultColor;

    @Transient
    private Integer count;


    //TODO: implement AuditorAware to persist createDate, CreatedBy, ...
}
