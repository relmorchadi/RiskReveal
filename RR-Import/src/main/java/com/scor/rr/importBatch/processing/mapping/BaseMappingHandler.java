package com.scor.rr.importBatch.processing.mapping;

import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.plt.ModelRAPSource;
import com.scor.rr.domain.entities.references.*;
import com.scor.rr.domain.entities.references.cat.ModellingSystemInstance;
import com.scor.rr.domain.entities.references.cat.mapping.*;
import com.scor.rr.domain.entities.references.omega.Country;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.repository.cat.ModellingSystemInstanceRepository;
import com.scor.rr.repository.cat.mapping.*;
import com.scor.rr.repository.omega.CurrencyRepository;
import com.scor.rr.repository.plt.ModelRAPRepository;
import com.scor.rr.repository.references.GeographicResolutionRepository;
import com.scor.rr.repository.references.RegionPerilMappingRepository;
import com.scor.rr.repository.references.SourceCountryPerilRegionPerilGroupMapRepository;
import com.scor.rr.repository.references.SourceCountryPerilRegionPerilMapRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by U002629 on 10/04/2015.
 */
@Service
public class BaseMappingHandler implements MappingHandler {

    private SourceCountryPerilRegionPerilMapRepository sourceCountryPerilRegionPerilMapRepository;
    private SourceCountryPerilRegionPerilGroupMapRepository sourceCountryPerilRegionPerilGroupMapRepository;
    private RegionPerilMappingRepository regionPerilMappingRepository;
    private RegionPerilGroupMappingRepository regionPerilGroupMappingRepository;
    private CountryMappingRepository countryMappingRepository;
    private PerilMappingRepository perilMappingRepository;
    private ModellingSystemInstanceRepository modellingSystemInstanceRepository;
    private EPMetricMappingRepository epMetricMappingRepository;
    private GeographicResolutionMappingRepository geographicResolutionMappingRepository;
    private GeographicResolutionRepository geographicResolutionRepository;
    private ModelRAPSourceMappingRepository modelRAPSourceMappingRepository;
    private ModelRAPRepository modelRAPRepository;
    private ExposureSummaryLookupRepository exposureSummaryLookupRepository;
    private CurrencyRepository currencyRepository;

    private String instanceId;

    private String vendor;
    private String system;
    private String version;

    private ModellingSystemInstance modellingSystemInstance;

    private Map<String, String> perilCodes;
    private Map<String, String> countryCodes;
    private Map<String, String> regionCodes;
    private Map<String, String> regionGroupCodes;
    private Map<String, String> epMetricCodes;
    private Map<String, String> geoResCodes;
    private Map<String, Currency> currencies;
    private Map<String, Map<String, ExposureSummaryLookup>> viewMetricCodes;

    public void setSourceCountryPerilRegionPerilMapRepository(SourceCountryPerilRegionPerilMapRepository sourceCountryPerilRegionPerilMapRepository) {
        this.sourceCountryPerilRegionPerilMapRepository = sourceCountryPerilRegionPerilMapRepository;
    }

    public void setSourceCountryPerilRegionPerilGroupMapRepository(SourceCountryPerilRegionPerilGroupMapRepository sourceCountryPerilRegionPerilGroupMapRepository) {
        this.sourceCountryPerilRegionPerilGroupMapRepository = sourceCountryPerilRegionPerilGroupMapRepository;
    }

    public void setRegionPerilMappingRepository(RegionPerilMappingRepository regionPerilMappingRepository) {
        this.regionPerilMappingRepository = regionPerilMappingRepository;
    }

    public void setRegionPerilGroupMappingRepository(RegionPerilGroupMappingRepository regionPerilGroupMappingRepository) {
        this.regionPerilGroupMappingRepository = regionPerilGroupMappingRepository;
    }

    public void setEpMetricMappingRepository(EPMetricMappingRepository epMetricMappingRepository) {
        this.epMetricMappingRepository = epMetricMappingRepository;
    }

    public void setCountryMappingRepository(CountryMappingRepository countryMappingRepository) {
        this.countryMappingRepository = countryMappingRepository;
    }

    public void setPerilMappingRepository(PerilMappingRepository perilMappingRepository) {
        this.perilMappingRepository = perilMappingRepository;
    }

    public void setModellingSystemInstanceRepository(ModellingSystemInstanceRepository modellingSystemInstanceRepository) {
        this.modellingSystemInstanceRepository = modellingSystemInstanceRepository;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setModelRAPSourceMappingRepository(ModelRAPSourceMappingRepository modelRAPSourceMappingRepository) {
        this.modelRAPSourceMappingRepository = modelRAPSourceMappingRepository;
    }

    public void setExposureSummaryLookupRepository(ExposureSummaryLookupRepository exposureSummaryLookupRepository) {
        this.exposureSummaryLookupRepository = exposureSummaryLookupRepository;
    }

    public void setGeographicResolutionMappingRepository(GeographicResolutionMappingRepository geographicResolutionMappingRepository) {
        this.geographicResolutionMappingRepository = geographicResolutionMappingRepository;
    }

    public void setGeographicResolutionRepository(GeographicResolutionRepository geographicResolutionRepository) {
        this.geographicResolutionRepository = geographicResolutionRepository;
    }

    public void setModelRAPRepository(ModelRAPRepository modelRAPRepository) {
        this.modelRAPRepository = modelRAPRepository;
    }

    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public void init() {
        perilCodes = new HashMap<>();
        countryCodes = new HashMap<>();
        regionCodes = new HashMap<>();
        regionGroupCodes = new HashMap<>();
        epMetricCodes = new HashMap<>();
        geoResCodes = new HashMap<>();
        currencies = new HashMap<>();
        viewMetricCodes = new HashMap<>();
        modellingSystemInstance = modellingSystemInstanceRepository.findById(instanceId).orElse(null);
        vendor = modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getVendor().getId();
        system = modellingSystemInstance.getModellingSystemVersion().getModellingSystem().getId();
        version = modellingSystemInstance.getModellingSystemVersion().getId();
    }

    @Override
    public String getRegionPerilGroupForCountryPeril(String country, String peril) {
        String rp = regionGroupCodes.get(country + peril);
        if (rp != null)
            return rp;

        SourceCountryPerilRegionPerilGroupMap map = sourceCountryPerilRegionPerilGroupMapRepository
                .findByModellingSystemIdAndSourceCountryCode2AndSourcePerilCode(
                        system, country, peril);

        if (map != null) {
            final RegionPerilGroup regionPeril = map.getTargetValue();
            if (regionPeril != null && regionPeril.getCode() != null) {
                rp = regionPeril.getCode();
            } else if (regionPeril != null) {
                rp = regionPeril.getId();
            }
            if (rp != null) {
                regionGroupCodes.put(country + peril, rp);
                return rp;
            }
        } else if (country.equals("TOTAL") || peril.equals("TOTAL")) {
            rp = "TOTAL";
            regionGroupCodes.put(country + peril, rp);
            return rp;
        }

        return country + peril;
    }

    @Override
    public String getMappedPeril(String peril) {
        String rp = perilCodes.get(peril);
        if (rp != null)
            return rp;

        PerilMapping map = perilMappingRepository.findByModellingSystemIdAndSourceValueCode(system, peril);

        if (map != null) {
            final Peril regionPeril = map.getTargetValue();
            if (regionPeril != null && regionPeril.getCode() != null) {
                rp = regionPeril.getCode();
            } else if (regionPeril != null) {
                rp = regionPeril.getId();
            }
            if (rp != null) {
                perilCodes.put(peril, rp);
                return rp;
            }
        }

        return peril;
    }

    String getCountryMapping(String country2, String country3) {
        CountryMapping mapping = null;

        if (country3 != null && !country3.trim().equals(""))
            mapping = countryMappingRepository.findByModellingSystemIdAndSourceISO2AAndSourceISO3A(system, country2, country3);

        if (mapping == null)
            mapping = countryMappingRepository.findByModellingSystemIdAndSourceISO2A(system, country2);

        String countryCode = null;
        if (mapping != null) {
            final Country country = mapping.getTargetValue();
            if (country != null && country.getCode() != null) {
                countryCode = country.getCode();
            } else if (country != null) {
                countryCode = country.getCountryId();
            }
        }

        return countryCode;
    }

    @Override
    public String getMappedCountry(String country) {
        String cc = countryCodes.get(country);
        if (cc != null)
            return cc;

        cc = getCountryMapping(country, null);

        if (cc != null) {
            countryCodes.put(country, cc);
            return cc;
        }

        return cc;
    }

    @Override
    public String getMappedCountry(String country2, String country3) {
        String cc = countryCodes.get(country2 + country3);
        if (cc != null)
            return cc;

        cc = getCountryMapping(country2, country3);

        if (cc != null) {
            countryCodes.put(country2 + country3, cc);
            return cc;
        }

        return cc;
    }


    String getRegionPerilMapping(String country, String adminCode, String peril) {
        SourceCountryPerilRegionPerilMap mapping = null;

//        if(adminCode!=null&&!adminCode.trim().equals(""))
//            mapping = sourceCountryPerilRegionPerilMapRepository
//                    .findByModellingSystemIdAndSourceCountryCode2AndSourceCountryCode3AndSourcePerilCode(
//                            system, country, adminCode, peril);

        if (mapping == null)
            mapping = sourceCountryPerilRegionPerilMapRepository
                    .findByModellingSystemIdAndSourceCountryCode2AndSourcePerilCode(
                            system, country, peril);

        String rp = null;
        if (mapping != null) {
            final RegionPeril regionPeril = mapping.getRegionPeril();
            if (regionPeril != null && regionPeril.getPerilCode() != null) {
                rp = regionPeril.getPerilCode();
            } else if (regionPeril != null) {
                rp = String.valueOf(regionPeril.getRegionPerilId());
            }
        }

        return rp;
    }


    @Override
    public String getRegionPerilForCountryPeril(String country, String peril) {
        String rp = regionCodes.get(country + peril);
        if (rp != null)
            return rp;

        rp = getRegionPerilMapping(country, null, peril);

        if (rp != null) {
            regionCodes.put(country + peril, rp);
            return rp;
        }

        return country + peril;
    }

    @Override
    public String getRegionPerilForCountryPeril(String country, String adminCode, String peril) {
        String rp = regionCodes.get(country + adminCode + peril);
        if (rp != null)
            return rp;

        rp = getRegionPerilMapping(country, adminCode, peril);

        if (rp != null) {
            regionCodes.put(country + adminCode + peril, rp);
            return rp;
        }

        return country + adminCode + peril;
    }

    @Override
    public String getRegionPerilForDLMProfile(String dlmProfile) {
        String rp = regionCodes.get(dlmProfile);
        if (rp != null)
            return rp;

        ModelRAPSourceMapping rap = modelRAPSourceMappingRepository.findByDlmProfileNameIgnoreCaseAndModellingSystemId(dlmProfile, system);

        if (rap != null) {
            rp = String.valueOf(rap.getTarget().getRegionPeril().getRegionPerilId());
            regionCodes.put(dlmProfile, rp);
            return rp;
        }

        return dlmProfile;
    }

    @Override
    public ModelRAPSource getModelRAPSourceForDLMProfile(String dlmProfile) {
        final ModelRAPSourceMapping mapping = modelRAPSourceMappingRepository.findByDlmProfileNameIgnoreCaseAndModellingSystemId(dlmProfile, system);
        if (mapping == null) {
            throw new RuntimeException("No Model Rap Source Mapping found for DLM profile " + dlmProfile);
        }
        return mapping.getTarget();
    }

    @Override
    public ModelRAP getModelRAPForDLMProfile(String dlmProfile) {
        final ModelRAPSource target = getModelRAPSourceForDLMProfile(dlmProfile);
        return getModelRAPForModelRapSource(target.getId());
    }

    @Override
    public ModelRAP getModelRAPForModelRapSource(String sourceId) {
        return modelRAPRepository.findByModelRAPSourceIdAndRegionPerilDefaultTrue(sourceId);
    }

    @Override
    public String getEPMetricForCode(String code) {
        String myCode = epMetricCodes.get(code);
        if (myCode != null)
            return myCode;

        EPMetricMapping map = epMetricMappingRepository
                .findByModellingSystemVersionIdAndSourceValueCode(
                        version, code);

        if (map != null) {
            myCode = map.getTargetValue().getCode();
            if (myCode != null) {
                epMetricCodes.put(code, myCode);
                return myCode;
            }
        }

        return code;
    }

    @Override
    public String getGeoResForCode(String code) {
        String myCode = geoResCodes.get(code);
        if (myCode != null)
            return myCode;

        GeographicResolutionMapping map = geographicResolutionMappingRepository
                .findByModellingSystemVersionIdAndSourceValueCode(version, code);

        if (map != null) {
            myCode = map.getTargetValue().getCode();
            if (myCode != null) {
                final GeographicResolution gr = geographicResolutionRepository.findById(myCode).orElse(null);
                if (gr != null) {
                    final String description = gr.getDescription();
                    geoResCodes.put(code, description);
                    return description;
                }
            }
        }

        return code;
    }

    @Override
    public ExposureSummaryLookup getViewMetricForCode(String view, String code) {
        //TODO: use BRE to retrieve the mapping table based on the view name
        Map<String, ExposureSummaryLookup> viewCache = viewMetricCodes.get(view);
        if (viewCache == null) {
            viewCache = new HashMap<>();
            viewMetricCodes.put(view, viewCache);
        }
        ExposureSummaryLookup myCode = viewCache.get(code);
        if (myCode != null)
            return myCode;
        final ExposureSummaryLookup lookup = exposureSummaryLookupRepository.findByExposureViewTitleAndExposureViewCode(view, code);
        if (lookup != null) {
            viewCache.put(code, lookup);
            return lookup;
        }
        return new ExposureSummaryLookup(null, null, null, code, null);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getSystem() {
        return system;
    }

    @Override
    public Currency getCurrencyForCode(String code) {
        Currency c = currencies.get(code);
        if (c == null) {
            c = currencyRepository.findByCode(code);
            if (c == null) {
                c = new Currency();
                c.setCurrencyId(code);
            }
            currencies.put(code, c);
        }
        return c;
    }
}
