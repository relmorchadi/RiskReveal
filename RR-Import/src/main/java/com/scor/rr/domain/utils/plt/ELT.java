package com.scor.rr.domain.utils.plt;

import com.scor.rr.domain.entities.plt.ALMFFile;
import com.scor.rr.domain.utils.plt.loss.LossTable;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ELT
 * 
 * @author HADDINI Zakariyae
 *
 */
@Data
public class ELT extends LossTable {

	private Integer pdSplit;
	private Integer biSplit;

	private ELTSourceMetadata eltSourceMetadata;

	private List<ELTSourceStatistics> eltSourceStatistics;

	private Map<String, EPCurve> eltEpCurvesByEpType;
	private Map<String, EPCurve> statsEpCurvesByEpType;
	private Map<String, ALMFFile> epCurveFilesByFinancialPerspective;


//	@Override
//	public int hashCode() {
//		return 31 * super.hashCode() + Objects.hash(eltSourceMetadata);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//
////		if (obj == null || (ALMFUtils.isNotNull(obj) && getClass() != obj.getClass()))
////			return false;
//
//		if (!super.equals(obj))
//			return false;
//
//		ELT other = (ELT) obj;
//
//		return Objects.equals(eltSourceMetadata, other.eltSourceMetadata);
//	}

}
