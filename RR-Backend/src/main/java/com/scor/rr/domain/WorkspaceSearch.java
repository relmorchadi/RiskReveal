package com.scor.rr.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class WorkspaceSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
}
