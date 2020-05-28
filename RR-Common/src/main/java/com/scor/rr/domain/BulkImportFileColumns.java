package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BulkImportFileColumns")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkImportFileColumns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BulkImportFileColumnId")
    private Long BulkImportFileColumnId;
    @Column(name = "ColumnName")
    private String columnName;
    @Column(name = "Importance")
    private String importance;
}
