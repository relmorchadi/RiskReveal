package com.scor.rr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ViewContextColumns")
public class ViewContextColumns {

    @Id
    @Column(name = "ViewContextColumnId")
    Long viewContextColumnId;

    @Column(name = "ViewContextId")
    Long viewContextId;

    @Column(name = "DisplayName")
    String displayName;

    @Column(name = "ColumnOrder")
    Integer columnOrder;

    @Column(name = "IsVisible")
    Boolean isVisible;

    @Column(name = "MinWidth")
    Integer minWidth;

    @Column(name = "MaxWidth")
    Integer maxWidth;

    @Column(name = "DefaultWidth")
    Integer defaultWidth;

    @Column(name = "SortOrder")
    Integer sortOrder;

    @Column(name = "SortType")
    String sortType;

    @Column(name = "ColumnName")
    String columnName;

    @Column(name = "DataColumnType", length = 50)
    String dataColumnType;

    @Column(name = "DataDisplayType")
    String dataDisplayType;

    @Column(name = "IsResizable")
    Boolean isResizable;
}
