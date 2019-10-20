package com.scor.rr.domain;

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
    private String field;

    protected SearchItem(SearchItem searchItem){
        this.id= searchItem.getId();
        this.key= searchItem.getKey();
        this.value= searchItem.getValue();
        this.operator= searchItem.getOperator();
        this.field= searchItem.getField();
    }

}
