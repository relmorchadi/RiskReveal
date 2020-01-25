package com.scor.rr.domain.importfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FileBasedImportConfig", schema = "dbonew", catalog = "RiskReveal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileBasedImportConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "FileBasedImportConfig")
    private Long fileBasedImportConfig;

    @Column(name = "ProjectImportRunId")
    private Long projectImportRunId;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "IsImportLocked")
    private boolean isImportLocked;

    @Column(name = "ProjectImportSourceConfigId")
    private Long projectImportSourceConfigId;

    @Column(name = "SelectedSourceFileImportId")
    private Long selectedSourceFileImportId;

    @Column(name = "SelectedSourceFileImportIdForRowSelect")
    private int selectedSourceFileImportIdForRowSelect;

    @Column(name = "SelectedFolderSourcePath", length = 500)
    private String selectedFolderSourcePath;

    @Column(name = "FolderSequence")
    private int folderSequence;

    @Column(name = "SelectedFileSourcePath", length = 500)
    private String selectedFileSourcePath;

    @Column(name = "FileSequence")
    private int fileSequence;

    @Column(name = "ImportedAtLeastOnce")
    private boolean importedAtLeastOnce;

    @Column(name = "Importing")
    private boolean importing;

    @Column(name = "LastProjectImportRunId")
    private Long lastProjectImportRunId;

    @Column(name = "LastUnlockDateForImport")
    private Date lastUnlockDateForImport;

    @Column(name = "LastUnlockBy")
    private int lastUnlockBy;
}
