package com.scor.rr.domain.utils.plt;

import com.scor.rr.domain.entities.plt.ALMFFile;
import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.plt.ModelRAPSource;
import com.scor.rr.domain.utils.plt.loss.LossTable;
import com.scor.rr.domain.utils.plt.loss.LossTableHeader;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * PLT
 * 
 * @author HADDINI Zakariyae
 *
 */
@Data
public class PLT extends LossTable {

	private Map<String, EPCurve> epCurvesByType;
	private Map<String, ALMFFile> epCurveFilesByType;

	private List<SummaryStatistics> summaryStatistics;

	public PLT() {
		epCurvesByType = new TreeMap<>();
		epCurveFilesByType = new TreeMap<>();
		summaryStatistics = new LinkedList<>();
	}

	public PLT(ModelRAPSource modelRAPSource, ModelRAP modelRAP, LossTableHeader header, ALMFFile headerFile,
			   ALMFFile dataFile, List<SummaryStatistics> summaryStatistics, Map<String, EPCurve> epCurvesByType,
			   Map<String, ALMFFile> epCurveFilesByType) {
		super(modelRAPSource, modelRAP, header, headerFile, dataFile);
		this.summaryStatistics = summaryStatistics;
		this.epCurvesByType = epCurvesByType;
		this.epCurveFilesByType = epCurveFilesByType;
	}

//	@Override
//	public int hashCode() {
//		return 31 * super.hashCode() + Objects.hash(epCurveFilesByType);
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
//		if (!super.equals(obj)) {
//			return false;
//		}
//
//		final PLT other = (PLT) obj;
//
//		return Objects.equals(this.epCurveFilesByType, other.epCurveFilesByType);
//	}

}
