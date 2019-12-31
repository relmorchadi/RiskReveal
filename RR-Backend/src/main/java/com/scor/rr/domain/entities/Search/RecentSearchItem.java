package com.scor.rr.domain.entities.Search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ZZ_RecentSearchItem")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class RecentSearchItem extends SearchItem {
    private Long recentSearchId;

    public RecentSearchItem(SearchItem searchItem, Long recentSearchId){
        super(searchItem);
        this.recentSearchId= recentSearchId;
    }
}
