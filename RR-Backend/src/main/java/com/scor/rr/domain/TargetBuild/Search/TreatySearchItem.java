package com.scor.rr.domain.TargetBuild.Search;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "dr")
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
