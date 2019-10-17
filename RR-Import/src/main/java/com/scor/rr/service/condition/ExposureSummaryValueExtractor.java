package com.scor.rr.service.condition;

import com.scor.rr.domain.entities.references.RegionPeril;
import com.scor.rr.domain.entities.rms.exposuresummary.RmsExposureSummaryItem;
import com.scor.rr.service.RegionPerilMappingCacheService;
import com.scor.rr.utils.ALMFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exposure Summary Value Extractor
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ExposureSummaryValueExtractor implements ConditionColumnExtractor {

	private static final Logger logger = LoggerFactory.getLogger(ExposureSummaryValueExtractor.class);

	private RmsExposureSummaryItem item;
	private RegionPerilMappingCacheService regionPerilMappingCacheService;

	public ExposureSummaryValueExtractor(RegionPerilMappingCacheService regionPerilMappingCacheService) {
		super();
		this.regionPerilMappingCacheService = regionPerilMappingCacheService;
	}

	public void setObject(RmsExposureSummaryItem item) {
		this.item = item;
	}

	@Override
	public String getColumnValue(String columnName) {
		switch (columnName) {
			case "Peril":
				return item.getPeril();
				
			case "AnalysisRegion":
				return item.getAnalysisRegionCode();
			
			case "Country":
				return item.getCountryCode();
			
			case "Admin1":
				return item.getAdmin1Code();
			
			case "RegionPerilCode":
				RegionPeril rp = regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
																	item.getCountryCode(), 
																	item.getAdmin1Code(), 
																	item.getPeril());
				
				if (ALMFUtils.isNotNull(rp))
					return rp.getRegionPerilCode();
				
				return "";
			
			case "RegionPerilGroupCode":
				RegionPeril rpg = regionPerilMappingCacheService.findRegionPerilByCountryCodeAdmin1CodePerilCode(
																	item.getCountryCode(), 
																	item.getAdmin1Code(), 
																	item.getPeril());
				if (ALMFUtils.isNotNull(rpg))
					return rpg.getRegionPerilGroupCode();
				
				return "";
			
			default:
				logger.warn("column '{}' not known on Portfolio Type", columnName);
				
				break;
		}
		
		return "";
	}

}
