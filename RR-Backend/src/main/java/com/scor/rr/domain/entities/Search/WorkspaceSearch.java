package com.scor.rr.domain.entities.Search;


import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class WorkspaceSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer userId;
    private Integer count;
    private String label;

    WorkspaceSearch() {
        this.count = 0;
    }

}
