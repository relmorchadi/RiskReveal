package com.scor.rr.domain.entities.Search;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@NoArgsConstructor
public class FacSearchItem extends SearchItem {

    private Long facSearchId;

    public FacSearchItem(SearchItem item, Long facSearchId) {
        super(item);
        this.facSearchId= facSearchId;
    }
}
