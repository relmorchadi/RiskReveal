package com.scor.rr.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(schema = "poc")
@Data
@NoArgsConstructor
public class FacSearch extends WorkspaceSearch {

    @OneToMany(mappedBy = "facSearchId")
    List<FacSearchItem> items;

}
