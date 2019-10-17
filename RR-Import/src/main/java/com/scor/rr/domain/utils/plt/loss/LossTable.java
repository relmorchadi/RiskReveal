package com.scor.rr.domain.utils.plt.loss;

import com.scor.rr.domain.entities.plt.ALMFFile;
import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.plt.ModelRAPSource;
import lombok.Data;

/**
 * LossTable
 * 
 * @author HADDINI Zakariyae
 *
 */
@Data
public class LossTable {

	private ModelRAPSource modelRAPSource;
	private ModelRAP modelRAP;
	private LossTableHeader header;
	private ALMFFile headerFile;
	private ALMFFile dataFile;

	public LossTable() {
	}

	public LossTable(ModelRAPSource modelRAPSource, ModelRAP modelRAP, LossTableHeader header, ALMFFile headerFile,
			ALMFFile dataFile) {
		this.modelRAPSource = modelRAPSource;
		this.modelRAP = modelRAP;
		this.header = header;
		this.headerFile = headerFile;
		this.dataFile = dataFile;
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(headerFile, dataFile);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//
//		if (!ALMFUtils.isNotNull(obj) || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass())) {
//			return false;
//		}
//
//		final LossTable other = (LossTable) obj;
//
//		return ALMFUtils.isNotNull(other)
//				? Objects.equals(headerFile, other.headerFile) && Objects.equals(dataFile, other.dataFile)
//				: false;
//	}

}
