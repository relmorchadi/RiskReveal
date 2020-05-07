package com.scor.rr.domain.entities.Search;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SavedSearch")
@Data
@EqualsAndHashCode(callSuper = false)
public class SavedSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SavedSearchId")
    private Long savedSearchId;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Count")
    private Integer count;

    @Column(name = "Label")
    private String label;

    @Column(name = "Type")
    private String type;

    @Column(name = "SavedDate")
    @CreatedDate
    Date savedDate;

    @OneToMany(mappedBy = "id")
    List<SavedSearchItem> items;

    public SavedSearch() {
        this.count = 0;
    }

}
