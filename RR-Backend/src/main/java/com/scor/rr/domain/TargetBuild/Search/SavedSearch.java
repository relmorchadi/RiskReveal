package com.scor.rr.domain.TargetBuild.Search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SavedSearch", schema = "poc")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedSearch;

    @Column(name = "field")
    private String field;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "value")
    private String value;

    @Column(name = "operator")
    private String operator;

    @Column(name = "userId")
    private Integer userId;

}
