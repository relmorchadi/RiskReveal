package com.scor.rr.domain.entities.Search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(as = FacSearchItem.class)
public abstract class SearchItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword")
    private String key;
    private String value;
    private String operator;

    protected SearchItem(SearchItem searchItem){
        this.id= searchItem.getId();
        this.key= searchItem.getKey();
        this.value= searchItem.getValue();
        this.operator= searchItem.getOperator();
    }

}
