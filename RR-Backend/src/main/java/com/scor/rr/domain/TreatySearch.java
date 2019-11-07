package com.scor.rr.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(schema = "dr")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreatySearch extends WorkspaceSearch {

    @OneToMany(mappedBy = "treatySearchId")
    List<TreatySearchItem> items;

}
