//package com.scor.rr.importBatch.processing.header.rms;
//
//import com.scor.almf.cdm.domain.cat.CATAnalysisRequest;
//import com.scor.almf.cdm.domain.cat.CATRequest;
//import com.scor.almf.cdm.domain.cat.ELTSourceMetadata;
//import com.scor.almf.cdm.domain.reference.Insured;
//import com.scor.almf.cdm.domain.reference.ModellingSystem;
//import com.scor.almf.cdm.domain.reference.ModellingSystemVersion;
//import com.scor.almf.cdm.domain.reference.ModellingVendor;
//import com.scor.almf.cdm.tools.sequence.MongoDBSequence;
//import com.scor.almf.ihub.processing.domain.xml.LossTableHeader;
//import com.scor.almf.ihub.processing.domain.xml.ObjectFactory;
//import com.scor.almf.ihub.processing.domain.xml.RmsDlmProfile;
//import com.scor.almf.ihub.treaty.processing.domain.ELTLoss;
//import com.scor.almf.ihub.treaty.processing.domain.PLTLoss;
//import com.scor.rr.domain.entities.cat.CATRequest;
//import com.scor.rr.domain.entities.references.cat.Insured;
//import com.scor.rr.domain.utils.plt.loss.LossTableHeader;
//import com.scor.rr.importBatch.processing.batch.ParameterBean;
//import com.scor.rr.importBatch.processing.batch.XMLWriter;
//import com.scor.rr.importBatch.processing.batch.rms.BaseRMSBeanImpl;
//import com.scor.rr.importBatch.processing.domain.ELTData;
//import com.scor.rr.importBatch.processing.domain.ELTLoss;
//import com.scor.rr.importBatch.processing.domain.PLTData;
//import com.scor.rr.importBatch.processing.header.HeaderExtractor;
//import org.apache.commons.lang3.StringUtils;
//import org.joda.time.DateTime;
//import org.springframework.dao.DataAccessException;
//
//import javax.xml.datatype.DatatypeConfigurationException;
//import javax.xml.datatype.DatatypeFactory;
//import javax.xml.datatype.XMLGregorianCalendar;
//import java.math.BigInteger;
//import java.util.*;
//
//
//public class RMSHeaderExtractor extends BaseRMSBeanImpl implements HeaderExtractor {
//
//    private static final Map<Integer, String> subPerilCodes = new HashMap<Integer, String>();
//    private static final Map<Integer, String> lossAmplificationsCodes = new HashMap<Integer, String>();
//    private static final Map<Integer, String> eventTypeFiltersCodes = new HashMap<Integer, String>();
//    private static final Map<Integer, String> assumeUnknownPrimaryCodes = new HashMap<Integer, String>();
//    private static final Map<Integer, String> groundUpModifiersCode = new HashMap<Integer, String>();
//    private static final Map<Integer, String> coverageLeakageCode = new HashMap<Integer, String>();
//
//
//    static {
//        subPerilCodes.put(0,"None");
//        subPerilCodes.put(1,"EQ Shake");
//        subPerilCodes.put(2,"EQ Fire");
//        subPerilCodes.put(4,"EQ Sprinkler Leakage (EQSL)");
//        subPerilCodes.put(256,"WS Surge");
//        subPerilCodes.put(512,"WS Wind");
//        subPerilCodes.put(65536,"TO Tornado");
//        subPerilCodes.put(131072,"TO Hail");
//        subPerilCodes.put(1048576,"FL Off Plain Flood");
//        subPerilCodes.put(2097152,"FL On Plain Flood");
//        subPerilCodes.put(16777216,"WT Wind");
//        subPerilCodes.put(33554432,"WT Snow");
//        subPerilCodes.put(67108864,"WT Ice");
//        subPerilCodes.put(134217728,"WT Freeze");
//        subPerilCodes.put(268435456,"CS Tornado");
//        subPerilCodes.put(536870912,"CS Hail");
//        subPerilCodes.put(1073741824,"CS Wind");
//
//        lossAmplificationsCodes.put(0,"None");
//        lossAmplificationsCodes.put(1,"Bldg");
//        lossAmplificationsCodes.put(2,"Cont");
//        lossAmplificationsCodes.put(4,"BI");
//        lossAmplificationsCodes.put(268435456, "Mixed");
//        lossAmplificationsCodes.put(536870912, "Unknown");
//        lossAmplificationsCodes.put(1073741824,"N.A.");
//
//        eventTypeFiltersCodes.put(0,"None");
//        eventTypeFiltersCodes.put(1,"Low frequency event filter");
//        eventTypeFiltersCodes.put(2,"High frequency event filter");
//
//        assumeUnknownPrimaryCodes.put(0,"None");
//        assumeUnknownPrimaryCodes.put(1,"Construction Class");
//        assumeUnknownPrimaryCodes.put(2,"Occupancy Type");
//        assumeUnknownPrimaryCodes.put(4,"Year Built");
//        assumeUnknownPrimaryCodes.put(8,"Height");
//        assumeUnknownPrimaryCodes.put(16,"Square Footage");
//        assumeUnknownPrimaryCodes.put(32,"Floors Occupied");
//
//        groundUpModifiersCode.put(0,"None");
//        groundUpModifiersCode.put(1,"RMS Default");
//        groundUpModifiersCode.put(2,"Custom with NFIP Takeup");
//        groundUpModifiersCode.put(4,"Custom with Coverage Leakage");
//        groundUpModifiersCode.put(8,"User Defined");
//
//        coverageLeakageCode.put(0,"None");
//        coverageLeakageCode.put(1,"Single Family");
//        coverageLeakageCode.put(2,"Multi Family");
//        coverageLeakageCode.put(4,"Other");
//    }
//
//    private ELTData eltData;
//    private PLTData pltData;
//    private String analysisInformationQuery;
//    private String modellingSettingsQuery;
//    private String analysisRegionsQuery;
//    private String csOptionsQuery;
//    private String eqOptionsQuery;
//    private String flOptionsQuery;
//    private String trOptionsQuery;
//    private String wsOptionsQuery;
//    private String wtOptionsQuery;
//
//    private XMLWriter writer;
//
//    private ParameterBean rmsParameters;
//
////    protected MongoDBSequence sequence;
//
//    public RMSHeaderExtractor() {
//        ObjectFactory factory = new ObjectFactory();
//        RmsDlmProfile dlmProfile = factory.createRmsDlmProfile();
//    }
//
//    public void setAnalysisInformationQuery(String analysisInformationQuery) {
//        this.analysisInformationQuery = analysisInformationQuery;
//    }
//
//    public void setModellingSettingsQuery(String modellingSettingsQuery) {
//        this.modellingSettingsQuery = modellingSettingsQuery;
//    }
//
//    public void setAnalysisRegionsQuery(String analysisRegionsQuery) {
//        this.analysisRegionsQuery = analysisRegionsQuery;
//    }
//
//    public void setCsOptionsQuery(String csOptionsQuery) {
//        this.csOptionsQuery = csOptionsQuery;
//    }
//
//    public void setEqOptionsQuery(String eqOptionsQuery) {
//        this.eqOptionsQuery = eqOptionsQuery;
//    }
//
//    public void setFlOptionsQuery(String flOptionsQuery) {
//        this.flOptionsQuery = flOptionsQuery;
//    }
//
//    public void setTrOptionsQuery(String trOptionsQuery) {
//        this.trOptionsQuery = trOptionsQuery;
//    }
//
//    public void setWsOptionsQuery(String wsOptionsQuery) {
//        this.wsOptionsQuery = wsOptionsQuery;
//    }
//
//    public void setWtOptionsQuery(String wtOptionsQuery) {
//        this.wtOptionsQuery = wtOptionsQuery;
//    }
//
//    public void setEltData(ELTData eltData) {
//        this.eltData = eltData;
//    }
//
//    public void setPltData(PLTData pltData) {
//        this.pltData = pltData;
//    }
//
//    public void setWriter(XMLWriter writer) {
//        this.writer = writer;
//    }
//
//    public void setRmsParameters(ParameterBean rmsParameters) {
//        this.rmsParameters = rmsParameters;
//    }
//
//    @Override
//    public boolean extractHeader() {
//        for (String s : eltData.getRegionPerils()) {
//            final ELTLoss lossDataForRp = eltData.getLossDataForRp(s);
//            Integer anlsId = lossDataForRp.getAnalysisId();
//            extractAnalysisInformation(anlsId, s);
//            extractDLMProfile(anlsId, s, lossDataForRp.getCurrency());
//        }
//
//        return false;
//    }
//
//    protected void extractCoreData(){
//    }
//
//    protected void extractAnalysisInformation(Integer anlsId, String rp) {
//        ObjectFactory factory = new ObjectFactory();
//        LossTableHeader header = factory.createLossTableHeader();
//
//        Map<String, Object> settings = getPerilOptions(anlsId, analysisInformationQuery);
//
//        BigInteger eltHeaderId = BigInteger.valueOf(sequence.increaseCounter("ELTHeader"));
//        header.setHeaderId(eltHeaderId);
//        header.setParentId(eltHeaderId);
//        header.setRootId(eltHeaderId);
//
//        XMLGregorianCalendar extractDate=null;
//        final GregorianCalendar cal = new GregorianCalendar();
//        try {
//            extractDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
//        } catch (DatatypeConfigurationException e) {
//            e.printStackTrace();
//        }
//        //header.setCreateDate(extractDate);
//        final CATRequest request = loadRequest();
//        if(request instanceof CATRequest){
//            CATRequest car = (CATRequest)request;
//            final Insured insured = car.getUwAnalysis().getContract().getInsured();
//            if (insured != null) {
//                header.setClientName(insured.getName());
//                header.setClientId(insured.getCode());
//            }
//            final Integer year = car.getUwAnalysis().getContract().getYear();
//            if (year != null) {
//                //header.setuWYear(BigInteger.valueOf(year.longValue()));
//            }
//        }
//        header.setUserId(request.getReauestedBy().getCode());
//        header.setSourceModelProvider(ModellingVendor.getVendorFromInstance(request.getModellingSystemInstance()));
//        header.setSourceModelSystem(ModellingSystem.getSystemFromInstance(request.getModellingSystemInstance()));
//        header.setSourceModelSystemVersion(ModellingSystemVersion.getVersionFromInstance(request.getModellingSystemInstance()));
//        header.setSourceModelModule("DLM");
//        header.setSourceLossTableType("ELT");
//        header.setLossTableType("ELT");
//        header.setSourceLossTableBasis("AM");
//        header.setSourceSimulationYears("");
//        header.setSourceSimulationPeriodBasis("");
//
//        header.setSourceEventSet(settings.get("EVENT_SET_ID").toString());
//        header.setAnalysisCurrency((String)settings.get("ANALYSIS_CURRENCY"));
//        header.setFinancialPerspective(fpELT);
//        header.setRegion((String)settings.get("REGION"));
//        header.setPeril((String)settings.get("PERIL"));
//
////        header.setRegionPeril(factory.createLossTableHeaderRegionPeril());
////        header.getRegionPeril().setValue("1");
////        header.getRegionPeril().setId(new BigInteger("1"));
//
//        header.setSubPerils(factory.createLossTableHeaderSubPerils());
//        final Integer subPerilsCode = (Integer) settings.get("SUB_PERIL");
//        Map<Integer, String> decodedSubPerils = null;
//        if(subPerilsCode!=null) {
//            decodedSubPerils = decodeMask(subPerilsCode, subPerilCodes);
//            for (Map.Entry<Integer, String> sp : decodedSubPerils.entrySet()) {
//                header.getSubPerils().getSubPeril().add(sp.getValue());
//            }
//        }
//
//        header.setLossAmplifications(factory.createLossTableHeaderLossAmplifications());
//        final Integer lossAmplificationsCode = (Integer) settings.get("LOSS_AMPLIFICATION");
//        Map<Integer, String> decodedLossAmps = null;
//        if(lossAmplificationsCode!=null) {
//            decodedLossAmps = decodeMask(lossAmplificationsCode, lossAmplificationsCodes);
//            for (Map.Entry<Integer, String> sp : decodedLossAmps.entrySet()) {
//                header.getLossAmplifications().getLossAmplification().add(sp.getValue());
//            }
//        }
//
//        header.setSourceHasEventRemapping("FALSE");
//        header.setSourceHasGrouping("FALSE");
//
//        header.setRelatedLossTables(factory.createLossTableHeaderRelatedLossTables());
//
//        header.setSourceAnalysis(factory.createLossTableHeaderSourceAnalysis());
//        header.getSourceAnalysis().setAnalysisCurrency((String)settings.get("ANALYSIS_CURRENCY"));
//        final Integer currencyUnit = (Integer) settings.get("ANALYSIS_CURRENCY_UNIT");
//        if (currencyUnit != null) {
//            header.getSourceAnalysis().setAnalysisCurrencyUnit(BigInteger.valueOf(currencyUnit.longValue()));
//        }
//        header.getSourceAnalysis().setAnalysisExposureId(settings.get("EXPOSURE_ID").toString());
//        header.getSourceAnalysis().setAnalysisExposureType(settings.get("EXPOSURE_TYPE").toString());
//        header.getSourceAnalysis().setAnalysisId(settings.get("ANALYSIS_ID").toString());
//        header.getSourceAnalysis().setAnalysisFinancialPerspective(fpELT);
//        header.getSourceAnalysis().setAnalysisProfileName((String)settings.get("DLM_PROFILE_NAME"));
//
//
//        final Long d =((Date) settings.get("ANALYSIS_RUN_DATE")).getTime();
//        if (d != null) {
//            final GregorianCalendar cal2 = new GregorianCalendar();
//            cal2.setTimeInMillis(d);
//            XMLGregorianCalendar runDate=null;
//            try {
//                runDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal2);
//            } catch (DatatypeConfigurationException e) {
//                e.printStackTrace();
//            }
//            header.getSourceAnalysis().setAnalysisRunDate(runDate);
//        }
//
//        header.getSourceAnalysis().setOriginalCompanyName(header.getClientName());
//        header.getSourceAnalysis().setOriginalProgram("");
//        header.getSourceAnalysis().setSourceDataName(RDM);
//        header.getSourceAnalysis().setSourceDataType("DB");
//        header.getSourceAnalysis().setSourceImportDate(extractDate);
//        header.getSourceAnalysis().setUserComment("");
//        header.getSourceAnalysis().setUserDescription("");
//
//        final ELTLoss lossDataForRp = eltData.getLossDataForRp(rp);
//        header.setTruncationHistory(factory.createLossTableHeaderTruncationHistory());
//        header.getTruncationHistory().setAAL(lossDataForRp.getTruncatedAAL());
//        header.getTruncationHistory().setCreateDate(extractDate);
//        header.getTruncationHistory().setEventCount(BigInteger.valueOf(lossDataForRp.getTruncatedEvents()));
//        header.getTruncationHistory().setStandardDeviation(lossDataForRp.getTruncatedSD());
//        header.getTruncationHistory().setThresholdAmount(lossDataForRp.getTruncationThreshold());
//        header.getTruncationHistory().setThresholdCurrency(lossDataForRp.getTruncationCurrency());
//
//        getModellingResultsByRegionPeril().get(rp).getElt().setEltSourceMetadata(new ELTSourceMetadata(
//                RDM,
//                "",
//                "",
//                header.getClientName(),
//                "",
//                cal.getTime(),
//                (Integer) settings.get("ANALYSIS_ID"),
//                "",
//                "",
//                new Date(d),
//                (String) settings.get("ANALYSIS_CURRENCY"),
//                settings.get("ANALYSIS_CURRENCY_UNIT").toString(),
//                settings.get("ANALYSIS_TYPE").toString(),
//                (String) settings.get("PERIL"),
//                decodedSubPerils == null ? "" : decodedSubPerils.values().toString(),
//                (String) settings.get("REGION"),
//                rp,
//                fpELT,
//                decodedLossAmps == null ? "" : decodedLossAmps.values().toString(),
//                ((Double) settings.get("SCALE_FACTOR")).intValue(),
//                (Integer) settings.get("EXPOSURE_ID"),
//                settings.get("EXPOSURE_TYPE").toString(),
//                settings.get("ANALYSIS_MODE").toString(),
//                settings.get("IS_GROUP").equals(1) ? true : false,
//                settings.get("EVENT_SET_ID").toString()
//        ));
//
//
//        LossTableHeader pltHeader = factory.createLossTableHeader();
//        header.copyTo(pltHeader);
//        pltHeader.setHeaderId(BigInteger.valueOf(sequence.increaseCounter("PLTHeader")));
//        pltHeader.setParentId(eltHeaderId);
//        pltHeader.setRootId(eltHeaderId);
//        pltHeader.setLossTableType("PLT");
//
//        final PLTLoss pltlossDataForRp = pltData.getLossDataForRP(rp);
//        pltHeader.setTruncationHistory(factory.createLossTableHeaderTruncationHistory());
//        pltHeader.getTruncationHistory().setAAL(pltlossDataForRp.getTruncatedAAL());
//        pltHeader.getTruncationHistory().setCreateDate(extractDate);
//        pltHeader.getTruncationHistory().setEventCount(BigInteger.valueOf(pltlossDataForRp.getTruncatedEvents()));
//        pltHeader.getTruncationHistory().setStandardDeviation(pltlossDataForRp.getTruncatedSD());
//        pltHeader.getTruncationHistory().setThresholdAmount(pltlossDataForRp.getTruncationThreshold());
//        pltHeader.getTruncationHistory().setThresholdCurrency(pltlossDataForRp.getTruncationCurrency());
//
//        try {
//            writer.write(header, "ELT", rp, fpELT, header.getAnalysisCurrency() , "MODEL", "HDR", ".xml");
//            writer.write(pltHeader, "PLT", rp, fpELT, header.getAnalysisCurrency() , "INTERNAL", "HDR", ".xml");
//
//        } catch (Exception e) {
//            //log error here
//        }
//
//        addMessage("ELT HDR", "Analysis Information extracted OK");
//    }
//
//    protected void extractMetaData(){
//    }
//
//    private void extractDLMProfile(Integer anlsId, String rp, String curr){
//        ObjectFactory factory = new ObjectFactory();
//        RmsDlmProfile dlmProfile = factory.createRmsDlmProfile();
//
//        Map<String, Object> settings = getPerilOptions(anlsId, modellingSettingsQuery);
//        if (settings!=null) {
//            Object AnalysisId = settings.get("AnalysisId");
//            Object VulnerabilityCurveSetCase = settings.get("VulnerabilityCurveSetCase");
//
//            final Integer profileId = (Integer) settings.get("ProfileId");
//            if(profileId!=null)
//                dlmProfile.setProfileId(BigInteger.valueOf(profileId));
//            dlmProfile.setProfileName((String) settings.get("ProfileName"));
//            dlmProfile.setProfileDescription((String) settings.get("ProfileDescription"));
//
//            dlmProfile.setInsuranceType(factory.createRmsDlmProfileInsuranceType());
//            dlmProfile.getInsuranceType().setName((String) settings.get("InsuranceType"));
//            final Integer insuranceTypeId = (Integer) settings.get("InsuranceTypeId");
//            if(insuranceTypeId!=null)
//                dlmProfile.getInsuranceType().setValue(BigInteger.valueOf(insuranceTypeId));
//
//            dlmProfile.setPeril(factory.createRmsDlmProfilePeril());
//            dlmProfile.getPeril().setName((String) settings.get("Peril"));
//            dlmProfile.getPeril().setValue((String) settings.get("PerilCode"));
//
//            dlmProfile.setRegion(factory.createRmsDlmProfileRegion());
//            dlmProfile.getRegion().setName((String) settings.get("Region"));
//            dlmProfile.getRegion().setValue(StringUtils.trimToNull((String) settings.get("RegionCode")));
//
//            dlmProfile.setAnalysisType(factory.createRmsDlmProfileAnalysisType());
//            dlmProfile.getAnalysisType().setName((String) settings.get("AnalysisType"));
//            final Integer analysisTypeCode = (Integer) settings.get("AnalysisTypeCode");
//            if(analysisTypeCode!=null)
//                dlmProfile.getAnalysisType().setValue(BigInteger.valueOf(analysisTypeCode));
//
//            dlmProfile.setAnalysisMode(factory.createRmsDlmProfileAnalysisMode());
//            dlmProfile.getAnalysisMode().setName((String) settings.get("AnalysisMode"));
//            final Integer analysisModeCode = (Integer) settings.get("AnalysisModeCode");
//            if(analysisModeCode!=null)
//                dlmProfile.getAnalysisMode().setValue(BigInteger.valueOf(analysisModeCode));
//
//            dlmProfile.setAnalysisModeValue((Double) settings.get("AnalysisModeValue"));
//            try {
//                dlmProfile.setEventDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new DateTime(settings.get("EventDate")).toGregorianCalendar()));
//            } catch (DatatypeConfigurationException e) {
//                e.printStackTrace();
//            }
//
//            dlmProfile.setEventRateSet(factory.createRmsDlmProfileEventRateSet());
//            dlmProfile.getEventRateSet().setName((String) settings.get("EventRateSet"));
//            final Integer eventRateSetId = (Integer) settings.get("EventRateSetId");
//            if(eventRateSetId!=null)
//                dlmProfile.getEventRateSet().setValue(BigInteger.valueOf(eventRateSetId));
//
//            dlmProfile.setVulnerabilityCurve(factory.createRmsDlmProfileVulnerabilityCurve());
//            dlmProfile.getVulnerabilityCurve().setName((String) settings.get("VulnerabilityCurveSet"));
//            final Integer vulnerabilityCurveSetId = (Integer) settings.get("VulnerabilityCurveSetId");
//            if(vulnerabilityCurveSetId!=null)
//                dlmProfile.getVulnerabilityCurve().setValue(BigInteger.valueOf(vulnerabilityCurveSetId));
//
//            dlmProfile.setSubPerils(factory.createRmsDlmProfileSubPerils());
//            final Integer subPerilsCode = (Integer) settings.get("SubPerilsCode");
//            if(subPerilsCode!=null) {
//                dlmProfile.getSubPerils().setCode(BigInteger.valueOf(subPerilsCode));
//                for (Map.Entry<Integer, String> sp : decodeMask(dlmProfile.getSubPerils().getCode().intValue(), subPerilCodes).entrySet()) {
//                    RmsDlmProfile.SubPerils.SubPeril subPeril = factory.createRmsDlmProfileSubPerilsSubPeril();
//                    subPeril.setName(sp.getValue());
//                    subPeril.setValue(BigInteger.valueOf(sp.getKey()));
//                    dlmProfile.getSubPerils().getSubPeril().add(subPeril);
//                }
//            }
//
//            dlmProfile.setTimeWindow((Double) settings.get("TimeWindow"));
//
//            dlmProfile.setFinancialPerspective(factory.createRmsDlmProfileFinancialPerspective());
//            dlmProfile.getFinancialPerspective().setName(StringUtils.trimToNull((String) settings.get("FinancialPerspectiveCode")));
//            dlmProfile.getFinancialPerspective().setValue(StringUtils.trimToNull((String) settings.get("FinancialPerspectiveCode")));
//
//            dlmProfile.setLossAmplifications(factory.createRmsDlmProfileLossAmplifications());
//            final Integer lossAmplificationsCode = (Integer) settings.get("LossAmplificationsCode");
//            if(lossAmplificationsCode!=null) {
//                dlmProfile.getLossAmplifications().setCode(BigInteger.valueOf(lossAmplificationsCode));
//                for (Map.Entry<Integer, String> sp : decodeMask(dlmProfile.getLossAmplifications().getCode().intValue(), lossAmplificationsCodes).entrySet()) {
//                    RmsDlmProfile.LossAmplifications.LossAmplification lossAmplification = factory.createRmsDlmProfileLossAmplificationsLossAmplification();
//                    lossAmplification.setName(sp.getValue());
//                    lossAmplification.setValue(BigInteger.valueOf(sp.getKey()));
//                    dlmProfile.getLossAmplifications().getLossAmplification().add(lossAmplification);
//                }
//            }
//
//            dlmProfile.setExposureAdjustment(factory.createRmsDlmProfileExposureAdjustment());
//            dlmProfile.getExposureAdjustment().setName((String) settings.get("ExposureAdjustment"));
//            final Integer exposureAdjustmentId = (Integer) settings.get("ExposureAdjustmentId");
//            if(exposureAdjustmentId!=null)
//                dlmProfile.getExposureAdjustment().setValue(BigInteger.valueOf(exposureAdjustmentId));
//
//            dlmProfile.setExposureTime(factory.createRmsDlmProfileExposureTime());
//            dlmProfile.getExposureTime().setName((String) settings.get("ExposureTime"));
//            final Integer exposureTimeId = (Integer) settings.get("ExposureTimeId");
//            if(exposureTimeId!=null)
//                dlmProfile.getExposureTime().setValue(BigInteger.valueOf(exposureTimeId));
//
//            dlmProfile.setExposureDay(factory.createRmsDlmProfileExposureDay());
//            dlmProfile.getExposureDay().setName((String) settings.get("ExposureDay"));
//            final Integer exposureDayId = (Integer) settings.get("ExposureDayId");
//            if(exposureDayId!=null)
//                dlmProfile.getExposureDay().setValue(BigInteger.valueOf(exposureDayId));
//
//            dlmProfile.setPercentSubject((Double) settings.get("PercentSubject"));
//
//            dlmProfile.setInjuryCostScheme(factory.createRmsDlmProfileInjuryCostScheme());
//            dlmProfile.getInjuryCostScheme().setName((String) settings.get("InjuryCostScheme"));
//            final Integer injuryCostSchemeId = (Integer) settings.get("InjuryCostSchemeId");
//            if(injuryCostSchemeId!=null)
//                dlmProfile.getInjuryCostScheme().setValue(BigInteger.valueOf(injuryCostSchemeId));
//
//            dlmProfile.setEventTypeFilters(factory.createRmsDlmProfileEventTypeFilters());
//            final Integer eventTypeFiltersCode = (Integer) settings.get("EventTypeFiltersCode");
//            if(eventTypeFiltersCode!=null) {
//                dlmProfile.getEventTypeFilters().setCode(BigInteger.valueOf(eventTypeFiltersCode));
//                for (Map.Entry<Integer, String> sp : decodeMask(dlmProfile.getEventTypeFilters().getCode().intValue(), eventTypeFiltersCodes).entrySet()) {
//                    RmsDlmProfile.EventTypeFilters.EventTypeFilter eventTypeFilter = factory.createRmsDlmProfileEventTypeFiltersEventTypeFilter();
//                    eventTypeFilter.setName(sp.getValue());
//                    eventTypeFilter.setValue(BigInteger.valueOf(sp.getKey()));
//                    dlmProfile.getEventTypeFilters().getEventTypeFilter().add(eventTypeFilter);
//                }
//            }
//
//            dlmProfile.setAssumeUnknownPrimary(factory.createRmsDlmProfileAssumeUnknownPrimary());
//            final Integer assumeUnknownPrimaryCode = (Integer) settings.get("AssumeUnknownPrimaryCode");
//            if(assumeUnknownPrimaryCode!=null) {
//                dlmProfile.getAssumeUnknownPrimary().setCode(BigInteger.valueOf(assumeUnknownPrimaryCode));
//                for (Map.Entry<Integer, String> sp : decodeMask(dlmProfile.getAssumeUnknownPrimary().getCode().intValue(), assumeUnknownPrimaryCodes).entrySet()) {
//                    RmsDlmProfile.AssumeUnknownPrimary.ConstructionModifier constructionModifier = factory.createRmsDlmProfileAssumeUnknownPrimaryConstructionModifier();
//                    constructionModifier.setName(sp.getValue());
//                    constructionModifier.setValue(BigInteger.valueOf(sp.getKey()));
//                    dlmProfile.getAssumeUnknownPrimary().getConstructionModifier().add(constructionModifier);
//                }
//            }
//
//            dlmProfile.setAssumeUnknownSecondary((Boolean) settings.get("AssumeUnknownSecondary"));
//            dlmProfile.setScaleFactor((Double) settings.get("ScaleFactor"));
//            dlmProfile.setScaleBuildingValues((Double) settings.get("ScaleBuildingValues"));
//            dlmProfile.setScaleContentValues((Double) settings.get("ScaleContentValues"));
//            dlmProfile.setScaleBIValues((Double) settings.get("ScaleBIValues"));
//            dlmProfile.setAddressResidualDemandSurge((Boolean) settings.get("AddResidualDemandSurge"));
//
//            extractAnalysisRegionsSettings(anlsId, dlmProfile, factory);
//            extractPerilSpecificOptions(anlsId, dlmProfile, factory);
//        }
//
//        try {
//            writer.write(dlmProfile, "ELT", rp, fpELT, curr, "MODEL", "APS", ".xml");
//        } catch (Exception e) {
//            //log error here
//        }
//        addMessage("DLM PRF", "DLM Profile extracted OK");
//    }
//
//    private void extractAnalysisRegionsSettings(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        dlmProfile.setAnalysisRegions(factory.createRmsDlmProfileAnalysisRegions());
//        List<Map<String, Object>> regionsList = jdbcTemplate.queryForList(analysisRegionsQuery.replaceAll(":rdm:", rdm), anlsId);
//        for (Map<String, Object> region : regionsList) {
//            RmsDlmProfile.AnalysisRegions.AnalysisRegion analysisRegion = factory.createRmsDlmProfileAnalysisRegionsAnalysisRegion();
//            analysisRegion.setName((String) region.get("Region"));
//            analysisRegion.setValue((String) region.get("RegionCode"));
//            dlmProfile.getAnalysisRegions().getAnalysisRegion().add(analysisRegion);
//        }
//    }
//
//    private void extractPerilSpecificOptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        if(dlmProfile.getPeril().getValue()!=null&&dlmProfile.getPeril().getValue().trim().equals("CS"))
//            extractCSOptions(anlsId, dlmProfile, factory);
//        else if(dlmProfile.getPeril().getValue()!=null&&dlmProfile.getPeril().getValue().trim().equals("EQ"))
//            extractEQOptions(anlsId, dlmProfile, factory);
//        else if(dlmProfile.getPeril().getValue()!=null&&dlmProfile.getPeril().getValue().trim().equals("FL"))
//            extractFLOptions(anlsId, dlmProfile, factory);
//        else if(dlmProfile.getPeril().getValue()!=null&&dlmProfile.getPeril().getValue().trim().equals("TR"))
//            extractTROptions(anlsId, dlmProfile, factory);
//        else if(dlmProfile.getPeril().getValue()!=null&&dlmProfile.getPeril().getValue().trim().equals("WS"))
//            extractWSOptions(anlsId, dlmProfile, factory);
//        else if(dlmProfile.getPeril().getValue()!=null&&dlmProfile.getPeril().getValue().trim().equals("WT"))
//            extractWTOptions(anlsId, dlmProfile, factory);
//    }
//
//    private void extractCSOptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        RmsDlmProfile.ConvectiveStormOptions stormOptions = factory.createRmsDlmProfileConvectiveStormOptions();
//        dlmProfile.setConvectiveStormOptions(stormOptions);
//        Map<String, Object> csOptions = getPerilOptions(anlsId, csOptionsQuery);
//        if (csOptions!=null) {
//            final Integer eventNumber = (Integer) csOptions.get("EventNumber");
//            if(eventNumber!=null)
//                stormOptions.setEventNumber(BigInteger.valueOf(eventNumber));
//            stormOptions.setFootprintFile((String) csOptions.get("FootprintFile"));
//        }
//    }
//
//    private void extractEQOptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        RmsDlmProfile.EarthquakeOptions earthquakeOptions = factory.createRmsDlmProfileEarthquakeOptions();
//        dlmProfile.setEarthquakeOptions(earthquakeOptions);
//        Map<String, Object> eqOptions = getPerilOptions(anlsId, eqOptionsQuery);
//        if (eqOptions!=null) {
//            earthquakeOptions.setFootprintFile((String) eqOptions.get("FootprintFile"));
//            final Integer eventNumber = (Integer) eqOptions.get("EventNumber");
//            if(eventNumber!=null)
//                earthquakeOptions.setFaultNumber(BigInteger.valueOf(eventNumber));
//            earthquakeOptions.setIncludeModifier((Boolean) eqOptions.get("IncludeModifier"));
//            earthquakeOptions.setMagnitude((Double) eqOptions.get("Magnitude"));
//            earthquakeOptions.setRegion(factory.createRmsDlmProfileEarthquakeOptionsRegion());
//            earthquakeOptions.getRegion().setName((String) eqOptions.get("Region"));
//            earthquakeOptions.getRegion().setValue((String) eqOptions.get("RegionCode"));
//            final Integer returnPeriod = ((Double) eqOptions.get("ReturnPeriod")).intValue();
//            if(returnPeriod!=null)
//                earthquakeOptions.setReturnPeriod(BigInteger.valueOf(returnPeriod));
//            earthquakeOptions.setUserDefinedCV((Double) eqOptions.get("UserDefinedCV"));
//        }
//    }
//
//    private void extractFLOptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        RmsDlmProfile.FloodOptions floodOptions = factory.createRmsDlmProfileFloodOptions();
//        dlmProfile.setFloodOptions(floodOptions);
//        Map<String, Object> flOptions = getPerilOptions(anlsId, flOptionsQuery);
//        if (flOptions!=null) {
//            floodOptions.setFootprintFile((String) flOptions.get("FootprintFile"));
//            final Integer eventNumber = (Integer) flOptions.get("EventNumber");
//            if(eventNumber!=null)
//                floodOptions.setEventNumber(BigInteger.valueOf(eventNumber));
//        }
//    }
//
//    private void extractTROptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        RmsDlmProfile.TerrorismOptions terrorismOptions = factory.createRmsDlmProfileTerrorismOptions();
//        dlmProfile.setTerrorismOptions(terrorismOptions);
//        Map<String, Object> trOptions = getPerilOptions(anlsId, trOptionsQuery);
//        if (trOptions!=null) {
//            final Integer attackModes = (Integer) trOptions.get("AttackModes");
//            if(attackModes!=null)
//                terrorismOptions.setAttackModes(BigInteger.valueOf(attackModes));
//            final Integer dataResolution = (Integer) trOptions.get("DataResolution");
//            if(dataResolution!=null)
//                terrorismOptions.setDataResolution(BigInteger.valueOf(dataResolution));
//            terrorismOptions.setExcludePerRiskReinsurance((Boolean) trOptions.get("ExcludePerRiskReinsurance"));
//            terrorismOptions.setFireOnlySFPStates((Boolean) trOptions.get("FireOnlySFPStates"));
//            final Integer riskOutlook = (Integer) trOptions.get("RiskOutlook");
//            if(riskOutlook!=null)
//                terrorismOptions.setRiskOutlook(BigInteger.valueOf(riskOutlook));
//            terrorismOptions.setTerrorismCertificationThreshold((Double) trOptions.get("CertificationThreshold"));
//            final Integer threatType = (Integer) trOptions.get("ThreatType");
//            if(threatType!=null)
//                terrorismOptions.setThreatType(BigInteger.valueOf(threatType));
//        }
//    }
//
//    private void extractWSOptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        RmsDlmProfile.WindstormOptions windstormOptions = factory.createRmsDlmProfileWindstormOptions();
//        dlmProfile.setWindstormOptions(windstormOptions);
//        Map<String, Object> wsOptions = getPerilOptions(anlsId, wsOptionsQuery);
//        if (wsOptions!=null) {
//            windstormOptions.setFootprintFile((String) wsOptions.get("FootprintFile"));
//            windstormOptions.setAssume2Percent((Boolean) wsOptions.get("Assume2Percent"));
//
//            windstormOptions.setCoverageLeakage(factory.createRmsDlmProfileWindstormOptionsCoverageLeakage());
//            final Integer coverageLeakageCode = (Integer) wsOptions.get("CoverageLeakageCode");
//            if(coverageLeakageCode!=null) {
//                windstormOptions.getCoverageLeakage().setCode(BigInteger.valueOf(coverageLeakageCode));
//                for (Map.Entry<Integer, String> sp : decodeMask(windstormOptions.getCoverageLeakage().getCode().intValue(), RMSHeaderExtractor.coverageLeakageCode).entrySet()) {
//                    RmsDlmProfile.WindstormOptions.CoverageLeakage.Coverage coverage = factory.createRmsDlmProfileWindstormOptionsCoverageLeakageCoverage();
//                    coverage.setName(sp.getValue());
//                    coverage.setValue(BigInteger.valueOf(sp.getKey()));
//                    windstormOptions.getCoverageLeakage().getCoverage().add(coverage);
//                }
//            }
//
//            final Integer eventNumber = (Integer) wsOptions.get("EventNumber");
//            if(eventNumber!=null)
//                windstormOptions.setEventNumber(BigInteger.valueOf(eventNumber));
//
//            windstormOptions.setGroundUpModifiers(factory.createRmsDlmProfileWindstormOptionsGroundUpModifiers());
//            final Integer groundUpModifiersCode = (Integer) wsOptions.get("GroundUpModifiersCode");
//            if(groundUpModifiersCode!=null) {
//                windstormOptions.getGroundUpModifiers().setCode(BigInteger.valueOf(groundUpModifiersCode));
//                for (Map.Entry<Integer, String> sp : decodeMask(windstormOptions.getGroundUpModifiers().getCode().intValue(), RMSHeaderExtractor.groundUpModifiersCode).entrySet()) {
//                    RmsDlmProfile.WindstormOptions.GroundUpModifiers.GroundUpModifier modifier = factory.createRmsDlmProfileWindstormOptionsGroundUpModifiersGroundUpModifier();
//                    modifier.setName(sp.getValue());
//                    modifier.setValue(BigInteger.valueOf(sp.getKey()));
//                    windstormOptions.getGroundUpModifiers().getGroundUpModifier().add(modifier);
//                }
//            }
//
//            windstormOptions.setIgnoreLocalDefenses((Boolean) wsOptions.get("IgnoreLocalDefenses"));
//            final Integer minSaffCategory = (Integer) wsOptions.get("MinSaffCategory");
//            if(minSaffCategory!=null)
//                windstormOptions.setMinSaffCategory(BigInteger.valueOf(minSaffCategory));
//            windstormOptions.setMultiFamilyFactor((Double) wsOptions.get("MultiFamilyFactor"));
//            windstormOptions.setMultiFamilyRate((Double) wsOptions.get("MultiFamilyRate"));
//            windstormOptions.setOtherFactor((Double) wsOptions.get("OtherFactor"));
//            windstormOptions.setOtherRate((Double) wsOptions.get("OtherRate"));
//            windstormOptions.setResetFloodElevationToDefault((Boolean) wsOptions.get("ResetFloodElevationToDefault"));
//            windstormOptions.setSingleFamilyFactor((Double) wsOptions.get("SingleFamilyFactor"));
//            windstormOptions.setSingleFamilyRate((Double) wsOptions.get("SingleFamilyRate"));
//        }
//    }
//
//    private void extractWTOptions(Integer anlsId, RmsDlmProfile dlmProfile, ObjectFactory factory){
//        RmsDlmProfile.WinterstormOptions winterstormOptions = factory.createRmsDlmProfileWinterstormOptions();
//        dlmProfile.setWinterstormOptions(winterstormOptions);
//        Map<String, Object> wtOptions = getPerilOptions(anlsId, wtOptionsQuery);
//        if (wtOptions!=null) {
//            final Integer eventNumber = (Integer) wtOptions.get("EventNumber");
//            if(eventNumber!=null)
//                winterstormOptions.setEventNumber(BigInteger.valueOf(eventNumber));
//            winterstormOptions.setFootprintFile((String) wtOptions.get("FootprintFile"));
//        }
//    }
//
//    private Map<String, Object> getPerilOptions(Integer anlsId, String sql) {
//        try {
//            return jdbcTemplate.queryForMap(sql.replaceAll(":rdm:", rdm), anlsId);
//        } catch (DataAccessException e) {
//            //log error here
//        }
//        return null;
//    }
//
//    private Map<Integer, String> decodeMask(int mask, Map<Integer, String> codes){
//        Map<Integer, String> decodedValues = new HashMap<Integer, String>();
//        if(mask==0){
//            decodedValues.put(0, codes.get(mask));
//        }else {
//            for (Integer key : codes.keySet()) {
//                if(key==0)
//                    continue;
//                if ((mask & key) == key)
//                    decodedValues.put(key, codes.get(key));
//            }
//        }
//        return decodedValues;
//    }
//}
