package com.scor.rr.domain.TargetBuild.Search;


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
@Table(schema = "dr")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreatySearch extends WorkspaceSearch {

    @CreatedDate
    Date savedDate;

    @OneToMany(mappedBy = "treatySearchId")
    List<TreatySearchItem> items;

}
