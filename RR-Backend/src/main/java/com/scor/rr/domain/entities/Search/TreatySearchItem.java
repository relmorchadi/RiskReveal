package com.scor.rr.domain.entities.Search;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ZZ_TreatySearchItem")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class TreatySearchItem extends SearchItem {
    private Long treatySearchId;

    public TreatySearchItem(SearchItem searchItem, Long treatySearchId){
        super(searchItem);
        this.treatySearchId= treatySearchId;
    }
}
