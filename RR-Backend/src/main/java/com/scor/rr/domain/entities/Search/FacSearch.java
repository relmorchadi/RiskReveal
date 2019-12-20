package com.scor.rr.domain.entities.Search;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ZZ_FacSearch")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FacSearch extends WorkspaceSearch {

    @CreatedDate
    Date savedDate;

    @OneToMany(mappedBy = "facSearchId")
    List<FacSearchItem> items;

}
