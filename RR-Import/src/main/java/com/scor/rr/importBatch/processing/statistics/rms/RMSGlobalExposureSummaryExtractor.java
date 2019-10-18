package com.scor.rr.importBatch.processing.statistics.rms;

//import com.scor.almf.cdm.domain.cat.*;
//import com.scor.almf.cdm.domain.reference.*;
//import com.scor.almf.cdm.repository.cat.GlobalExposureViewRepository;
//import com.scor.almf.cdm.repository.reference.ExposureGlobalViewRepository;
//import com.scor.almf.cdm.repository.reference.ExposureViewDefinitionRepository;
//import com.scor.almf.cdm.repository.reference.ExposureViewExtractQueryRepository;
//import com.scor.almf.cdm.repository.reference.ExposureViewVersionRepository;
//import com.scor.almf.ihub.treaty.processing.batch.ParameterBean;
//import com.scor.almf.ihub.treaty.processing.batch.rms.BaseRMSBeanImpl;
//import com.scor.almf.ihub.treaty.processing.statistics.StatisticsExtractor;
//import com.scor.rr.importBatch.processing.batch.ParameterBean;
//import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
//import com.scor.rr.importBatch.processing.statistics.StatisticsExtractor;
//import com.scor.rr.repository.cat.GlobalExposureViewRepository;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//
//import java.util.*;

import com.scor.rr.domain.entities.cat.CATObjectGroup;
import com.scor.rr.domain.entities.cat.GlobalExposureView;
import com.scor.rr.domain.entities.exposure.ExposureViewDefinition;
import com.scor.rr.domain.entities.exposure.ExposureViewExtractQuery;
import com.scor.rr.domain.entities.exposure.ExposureViewVersion;
import com.scor.rr.domain.entities.references.ExposureGlobalView;
import com.scor.rr.domain.entities.references.cat.ExposureViewSummaryData;
import com.scor.rr.domain.entities.references.cat.GlobalViewSummary;
import com.scor.rr.domain.entities.references.cat.mapping.ExposureSummaryLookup;
import com.scor.rr.importBatch.processing.batch.ParameterBean;
import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
import com.scor.rr.importBatch.processing.statistics.StatisticsExtractor;
import com.scor.rr.repository.cat.GlobalExposureViewRepository;
import com.scor.rr.repository.references.ExposureGlobalViewRepository;
import com.scor.rr.repository.rms.ExposureViewDefinitionRepository;
import com.scor.rr.repository.rms.ExposureViewExtractQueryRepository;
import com.scor.rr.repository.rms.ExposureViewVersionRepository;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.*;

/**
 * Created by U002629 on 31/03/2015.
 */
@Data
public class RMSGlobalExposureSummaryExtractor extends BaseRMSBeanImpl implements StatisticsExtractor {
    private static final Logger log = LoggerFactory.getLogger(RMSGlobalExposureSummaryExtractor.class);


    @Autowired
    private ExposureGlobalViewRepository exposureGlobalViewRepository;
    @Autowired
    private ExposureViewDefinitionRepository exposureViewDefinitionRepository;
    @Autowired
    private ExposureViewVersionRepository exposureViewVersionRepository;
    @Autowired
    private ExposureViewExtractQueryRepository exposureViewExtractQueryRepository;
    @Autowired
    private GlobalExposureViewRepository globalExposureViewRepository;

    private ParameterBean rmsParameters;


    @Override
    public Boolean runExtraction(){
        List<ExposureGlobalView> exposureGlobalViews = exposureGlobalViewRepository.findAll();
        CATObjectGroup group = getCatObjectGroup();
        for (ExposureGlobalView exposureGlobalView : exposureGlobalViews) {

            GlobalExposureView globalExposureView = globalExposureViewRepository.findByCatRequestCatRequestIdAndDivisionNumberAndPeriodBasisPeriodBasisIdAndName(catReqId, new Integer(division), periodBasis, exposureGlobalView.getName());
            if(globalExposureView==null) {
                globalExposureView = new GlobalExposureView(catReqId, new Integer(division), periodBasis, version.intValue(), exposureGlobalView.getName(), null, new TreeMap<Integer, GlobalViewSummary>());
            }

            Set<String> rpSet = new TreeSet<String>();
            for (ExposureViewDefinition definition : exposureGlobalView.getDefinitions()) {
                ExposureViewVersion exposureViewVersion = exposureViewVersionRepository.findByDefinitionIdAndCurrent(definition.getId(), true);
                ExposureViewExtractQuery exposureViewExtractQuery = exposureViewExtractQueryRepository.findByModellingSystemVersionIdAndExposureViewVersionId(mappingHandler.getVersion(), exposureViewVersion.getId());

                String title = definition.getName();
                String sql = exposureViewExtractQuery.getQuery();
                Map<Integer, String> parameters = exposureViewExtractQuery.getParameters();

                GlobalViewSummary summary = new GlobalViewSummary();
                summary.setSummaryTitle(title);

                summary.setSummaryMetricValues(new TreeMap<String, ExposureViewSummaryData>());
                summary.setOrderNb(definition.getOrderNb());

                Set<ExposureSummaryLookup> exposureSummaryLookups = new TreeSet<>();

                List<String>keys = new LinkedList<>();
                List<String>values = new LinkedList<>();
                for (Map.Entry<String, String> placeholder : rmsParameters.getQueryPlaceHolders().entrySet()) {
                    keys.add(placeholder.getKey());
                    values.add(placeholder.getValue());
                }
                String replacedSQL = StringUtils.replaceEach(sql, keys.toArray(new String[keys.size()]), values.toArray(new String[values.size()]));

                Object[] params = new Object[parameters.size()];
                for (Integer key : parameters.keySet()) {
                    params[key] = rmsParameters.getQueryParameters().get(parameters.get(key));
                }

                SqlRowSet rows = jdbcTemplate.queryForRowSet(replacedSQL, params);
                while (rows.next()){
                    ExposureSummaryLookup lookup = mappingHandler.getViewMetricForCode(title, rows.getString(1));
                    if (lookup != null) {
                        Double tiv = rows.getDouble(4);//cell
                        Integer locationCount = rows.getInt(5);//cell
                        String metric = lookup.getTargetValueCode();
                        exposureSummaryLookups.add(lookup);
                        String regionPeril = mappingHandler.getRegionPerilGroupForCountryPeril(rows.getString(2), rows.getString(3));
                        rpSet.add(regionPeril);

                        ExposureViewSummaryData data = summary.getSummaryMetricValues().get(regionPeril);
                        if (data == null) {
                            data = new ExposureViewSummaryData();
                            data.setTitle(regionPeril);
                            data.resetTiv();
                            data.resetLocationCount();
                            data.resetAvgTIV();
                            summary.getSummaryMetricValues().put(regionPeril, data);
                        }

                        Double oldTiv = data.tivValue(metric);
                        Integer oldLC = data.locationCountValue(metric);
                        data.putTivValue(metric, oldTiv == null ? tiv : oldTiv + tiv);
                        data.putLocationCountValue(metric, oldLC == null ? locationCount : oldLC + locationCount);
                    }
                }

                LinkedList<String> metrics = new LinkedList<>();
                for (ExposureSummaryLookup metric : exposureSummaryLookups) {
                    metrics.add(metric.getTargetValueCode());
                }
                summary.setSummaryMetrics(metrics);
                summary.setSummaryLookups(new LinkedList<>(exposureSummaryLookups));

                for (ExposureViewSummaryData exposureViewSummaryData : summary.getSummaryMetricValues().values()) {
                    for (String metric : exposureViewSummaryData.convertedTiv().keySet()) {
                        exposureViewSummaryData.putAvgTivValue(metric, (exposureViewSummaryData.tivValue(metric) / exposureViewSummaryData.locationCountValue(metric)));
                    }
                }
                for (String s : rpSet) {
                    if(summary.getSummaryMetricValues().get(s)==null) {
                        ExposureViewSummaryData value = new ExposureViewSummaryData();
                        value.setTitle(s);
                        value.resetTiv();
                        value.resetLocationCount();
                        value.resetAvgTIV();
                        summary.getSummaryMetricValues().put(s, value);
                    }
                }

                globalExposureView.getGlobalViewSummariesByName().put(summary.getOrderNb(), summary);
            }

            globalExposureViewRepository.save(globalExposureView);
        }
        addMessage("GLB EXP", "Global Exposure Summary Views extracted OK");
        return true;
    }

    @Override
    public Boolean runConformedExtraction() {
        return null;
    }
}
