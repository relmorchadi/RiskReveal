package com.scor.rr.domain.entities.Search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SearchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Keyword")
    private String keyword;

    @Column(name = "Value")
    private String value;

    @Column(name = "Operator")
    private String operator;

    protected SearchItem(SearchItem searchItem){
        this.id= searchItem.getId();
        this.keyword= searchItem.getKeyword();
        this.value= searchItem.getValue();
        this.operator= searchItem.getOperator();
    }

}
