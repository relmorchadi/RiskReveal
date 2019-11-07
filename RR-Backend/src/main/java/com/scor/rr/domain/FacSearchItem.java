package com.scor.rr.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "dr")
@Data
@NoArgsConstructor
public class FacSearchItem extends SearchItem {

    private Long facSearchId;

    public FacSearchItem(SearchItem item, Long facSearchId) {
        super(item);
        this.facSearchId= facSearchId;
    }
}
