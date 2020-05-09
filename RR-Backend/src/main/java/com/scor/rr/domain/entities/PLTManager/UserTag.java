package com.scor.rr.domain.entities.PLTManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UserTag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserTagId")
    private Long userTagId;

    @Column(name = "TagId")
    private Long tagId;

    @Column(name = "UserId")
    private Long user;

    @Column(name = "UserOverrideColour", length = 8)
    private String userOverrideColour;

    @CreatedDate
    @Column(name = "CreatedDate", nullable = false, updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "ModifiedDate")
    private Date modifiedDate;
}
