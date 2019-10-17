package com.scor.rr.domain.entities.rms;

import com.scor.rr.domain.entities.ihub.ModelingResultDataSource;
import com.scor.rr.domain.entities.ihub.SelectedAssociationBag;
import com.scor.rr.domain.entities.workspace.ModelingExposureDataSource;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RmsProjectImportConfig database table
 * 
 * @author HADDINI Zakariyae && Hamiani Mohammed
 *
 */
@Entity
@Table(name = "RmsProjectImportConfig")
@Data
public class RmsProjectImportConfig {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RmsProjectImportConfigId")
	private String rmsProjectImportConfigId;
	@Column(name = "LastUnlockDateForImport")
	private String lastUnlockDateForImport;
	@Column(name = "ImportedAtLeastOnce")
	private Boolean importedAtLeastOnce;
	@Column(name = "ImportLocked")
	private Boolean importLocked;
	@Column(name = "LastUnlockBy")
	private String lastUnlockBy;
	@Column(name = "ProjectId")
	private String projectId;
	@Column(name = "ProjectImportSourceConfigId")
	private String projectImportSourceConfigId;
	@Column(name = "ExtractLocSum")
	private Boolean extractLocSum;
	@Column(name = "Importing")
	private Boolean importing;
	@Column(name = "LastProjectImportRunId")
	private String lastProjectImportRunId;
	@OneToMany(mappedBy = "rmsProjectImportConfig")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SelectedAssociationBag> selectedAssociationBags;
	@OneToMany(mappedBy = "rmsProjectImportConfig")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ModelingExposureDataSource> availableModelingExposureDataSources;
	@OneToMany(mappedBy = "rmsProjectImportConfig")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ModelingResultDataSource> availableModelingResultDataSources;
}
