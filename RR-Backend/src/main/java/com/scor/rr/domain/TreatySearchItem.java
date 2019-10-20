package com.scor.rr.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "poc")
@Data
@NoArgsConstructor
public class TreatySearchItem extends SearchItem {
    private Long treatySearchId;
    public TreatySearchItem(SearchItem searchItem, Long treatySearchId){
        super(searchItem);
        this.treatySearchId= treatySearchId;
    }
}
