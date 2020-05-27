package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BulkImportFile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkImportFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BulkImportFileId")
    private Long bulkImportFileId;
    @Column(name = "FilePath")
    private String filePath;
    @Column(name = "FileName")
    private String fileName;
    @Column(name = "HasPassedValidation")
    private Boolean hasPassedValidation;
    @Column(name = "RowsWithErrors")
    private Integer RowErrorsCount;
    @Column(name = "UserId")
    private Long userId;


    @OneToMany(mappedBy = "file")
    private List<ValidationError> errors;

    public BulkImportFile(String filePath, String fileName, Boolean hasPassedValidation) {
        this.filePath = filePath;
        this.fileName = fileName;
        this.hasPassedValidation = hasPassedValidation;
    }
}
