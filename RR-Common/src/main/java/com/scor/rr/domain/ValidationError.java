package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ValidationError")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ValidationErrorId")
    private Long validationErrorId;
    @Column(name = "ColumnName")
    private String columnName;
    @Column(name = "RowIndex")
    private Integer rowIndex;
    @Column(name = "ErrorDescription")
    private String errorDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BulkImportFileId")
    @JsonIgnore
    private BulkImportFile file;

    public ValidationError(String columnName, Integer rowIndex, String errorDescription) {
        this.columnName = columnName;
        this.rowIndex = rowIndex;
        this.errorDescription = errorDescription;
    }

    public ValidationError(String columnName, Integer rowIndex, String errorDescription, BulkImportFile file) {
        this.columnName = columnName;
        this.rowIndex = rowIndex;
        this.errorDescription = errorDescription;
        this.file = file;
    }
}
