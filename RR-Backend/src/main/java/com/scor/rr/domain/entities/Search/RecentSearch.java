package com.scor.rr.domain.entities.Search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ZZ_RecentSearch")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecentSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer userId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date searchDate;

    @OneToMany(mappedBy = "recentSearchId", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RecentSearchItem> items;

    @PrePersist
    protected void prePersist() {
        if (this.searchDate == null) searchDate = new Date();
    }
}
