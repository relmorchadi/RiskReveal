package com.scor.rr.domain.importfile;

import com.scor.rr.util.ListStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FileBasedImportConfig", schema = "dbonew", catalog = "RiskReveal")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileBasedImportConfig {
    @Id
    @Column(name = "FileBasedImportConfig")
    private int fileBasedImportConfig;

    @Column(name = "ProjectImportRunId")
    private int projectImportRunId;

    @Column(name = "ProjectId")
    private int projectId;

    @Column(name = "IsImportLocked")
    private boolean isImportLocked;

    @Column(name = "ProjectImportSourceConfigId")
    private int projectImportSourceConfigId;

    @Column(name = "SelectedSourceFileImportId")
    private int selectedSourceFileImportId;

    @Column(name = "SelectedSourceFileImportidForRowSelect")
    private int selectedSourceFileImportidForRowSelect;

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
    private int lastProjectImportRunId;

    @Column(name = "LastUnlockDateForImport")
    private Date lastUnlockDateForImport;

    @Column(name = "LastUnlockBy")
    private int lastUnlockBy;
}
