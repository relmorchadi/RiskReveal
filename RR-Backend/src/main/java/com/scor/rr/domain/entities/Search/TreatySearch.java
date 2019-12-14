package com.scor.rr.domain.entities.Search;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreatySearch extends WorkspaceSearch {

    @CreatedDate
    Date savedDate;

    @OneToMany(mappedBy = "treatySearchId")
    List<TreatySearchItem> items;

    @PrePersist
    protected void prePersist() {
        if (this.savedDate == null) savedDate = new Date();
    }

}
