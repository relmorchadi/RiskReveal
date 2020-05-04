package com.scor.rr.domain.entities.Search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "SavedSearchItem")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class SavedSearchItem extends SearchItem {

    @Column(name = "SavedSearchId")
    private Long savedSearchId;

    public SavedSearchItem(SearchItem searchItem, Long savedSearchId){
        super(searchItem);
        this.savedSearchId = savedSearchId;
    }
}