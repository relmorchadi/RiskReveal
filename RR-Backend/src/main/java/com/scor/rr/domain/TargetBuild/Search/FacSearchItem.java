package com.scor.rr.domain.TargetBuild.Search;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "tb")
@Data
@NoArgsConstructor
public class FacSearchItem extends SearchItem {

    private Long facSearchId;

    public FacSearchItem(SearchItem item, Long facSearchId) {
        super(item);
        this.facSearchId= facSearchId;
    }
}
