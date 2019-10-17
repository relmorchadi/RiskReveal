package com.scor.rr.importBatch.processing.mapping;

import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.plt.ModelRAPSource;
import com.scor.rr.domain.entities.references.cat.mapping.ExposureSummaryLookup;
import com.scor.rr.domain.entities.references.omega.Currency;

/**
 * Created by U002629 on 10/04/2015.
 */
public interface MappingHandler {
    String getRegionPerilGroupForCountryPeril(String country, String peril);

    String getMappedPeril(String peril);

    String getMappedCountry(String country);

    String getMappedCountry(String country2, String country3);

    String getRegionPerilForCountryPeril(String country, String peril);

    String getRegionPerilForCountryPeril(String country, String adminCode, String peril);

    String getRegionPerilForDLMProfile(String dlmProfile);

    ModelRAPSource getModelRAPSourceForDLMProfile(String dlmProfile);

    ModelRAP getModelRAPForDLMProfile(String dlmProfile);

    ModelRAP getModelRAPForModelRapSource(String sourceId);

    String getEPMetricForCode(String code);

    String getGeoResForCode(String code);

    ExposureSummaryLookup getViewMetricForCode(String view, String code);

    String getVersion();
    String getSystem();

    Currency getCurrencyForCode(String code);
}
