package com.scor.rr.importBatch.processing.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Created by U002629 on 25/02/2015.
 */
public class Descriptor {

    private Header header;
    private MetaData metadata;
    private String fileName;

    public Descriptor() {
    }

    public Descriptor(Header header, MetaData metadata, String fileName) {
        this.header = header;
        this.metadata = metadata;
        this.fileName = fileName;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaData metadata) {
        this.metadata = metadata;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, metadata, fileName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Descriptor other = (Descriptor) obj;
        return Objects.equals(this.header, other.header) && Objects.equals(this.metadata, other.metadata) && Objects.equals(this.fileName, other.fileName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Descriptor{");
        sb.append("header=").append(header);
        sb.append(", metadata=").append(metadata);
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    class Header {
        Integer InstanceID;
        Integer ParentID;
        Integer RootID;
        Date CreateDate;
        String UserID;
        String ClientName;
        String ClientID;
        Integer UWYear;
        String SourceModelProvider;
        String SourceModelSystem;
        String SourceModelSystemlVersion;
        String SourceModelModule;
        String SourceDataType;
        String SourceLossTableType;
        String LossTableType;
        String SourceLossTableBasis;
        Integer SourceSimulationYears;
        Integer SourceSimulationPeriodBasis;
        String SourceEventSet;
        String AnalysisCurrency;
        String FinancialPerspective;
        String Region;
        String Peril;
        String RegionPeril;
        String SubPeril;
        String SubRegion1;
        String SubRegion2;
        String SubRegion3;
        String SubRegion4;
        String LossAmplification;
        Boolean SourceHasEventRemapping;
        Boolean SourceHasGrouping;
        Integer[] GroupingParentID;

        public Header() {
        }

        public Header(Integer instanceID, Integer parentID, Integer rootID, Date createDate, String userID, String clientName, String clientID, Integer UWYear, String sourceModelProvider, String sourceModelSystem, String sourceModelSystemlVersion, String sourceModelModule, String sourceDataType, String sourceLossTableType, String lossTableType, String sourceLossTableBasis, Integer sourceSimulationYears, Integer sourceSimulationPeriodBasis, String sourceEventSet, String analysisCurrency, String financialPerspective, String region, String peril, String regionPeril, String subPeril, String subRegion1, String subRegion2, String subRegion3, String subRegion4, String lossAmplification, Boolean sourceHasEventRemapping, Boolean sourceHasGrouping, Integer[] groupingParentID) {
            InstanceID = instanceID;
            ParentID = parentID;
            RootID = rootID;
            CreateDate = createDate;
            UserID = userID;
            ClientName = clientName;
            ClientID = clientID;
            this.UWYear = UWYear;
            SourceModelProvider = sourceModelProvider;
            SourceModelSystem = sourceModelSystem;
            SourceModelSystemlVersion = sourceModelSystemlVersion;
            SourceModelModule = sourceModelModule;
            SourceDataType = sourceDataType;
            SourceLossTableType = sourceLossTableType;
            LossTableType = lossTableType;
            SourceLossTableBasis = sourceLossTableBasis;
            SourceSimulationYears = sourceSimulationYears;
            SourceSimulationPeriodBasis = sourceSimulationPeriodBasis;
            SourceEventSet = sourceEventSet;
            AnalysisCurrency = analysisCurrency;
            FinancialPerspective = financialPerspective;
            Region = region;
            Peril = peril;
            RegionPeril = regionPeril;
            SubPeril = subPeril;
            SubRegion1 = subRegion1;
            SubRegion2 = subRegion2;
            SubRegion3 = subRegion3;
            SubRegion4 = subRegion4;
            LossAmplification = lossAmplification;
            SourceHasEventRemapping = sourceHasEventRemapping;
            SourceHasGrouping = sourceHasGrouping;
            GroupingParentID = groupingParentID;
        }

        public Integer getInstanceID() {
            return InstanceID;
        }

        public void setInstanceID(Integer instanceID) {
            InstanceID = instanceID;
        }

        public Integer getParentID() {
            return ParentID;
        }

        public void setParentID(Integer parentID) {
            ParentID = parentID;
        }

        public Integer getRootID() {
            return RootID;
        }

        public void setRootID(Integer rootID) {
            RootID = rootID;
        }

        public Date getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(Date createDate) {
            CreateDate = createDate;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        public String getClientName() {
            return ClientName;
        }

        public void setClientName(String clientName) {
            ClientName = clientName;
        }

        public String getClientID() {
            return ClientID;
        }

        public void setClientID(String clientID) {
            ClientID = clientID;
        }

        public Integer getUWYear() {
            return UWYear;
        }

        public void setUWYear(Integer UWYear) {
            this.UWYear = UWYear;
        }

        public String getSourceModelProvider() {
            return SourceModelProvider;
        }

        public void setSourceModelProvider(String sourceModelProvider) {
            SourceModelProvider = sourceModelProvider;
        }

        public String getSourceModelSystem() {
            return SourceModelSystem;
        }

        public void setSourceModelSystem(String sourceModelSystem) {
            SourceModelSystem = sourceModelSystem;
        }

        public String getSourceModelSystemlVersion() {
            return SourceModelSystemlVersion;
        }

        public void setSourceModelSystemlVersion(String sourceModelSystemlVersion) {
            SourceModelSystemlVersion = sourceModelSystemlVersion;
        }

        public String getSourceModelModule() {
            return SourceModelModule;
        }

        public void setSourceModelModule(String sourceModelModule) {
            SourceModelModule = sourceModelModule;
        }

        public String getSourceDataType() {
            return SourceDataType;
        }

        public void setSourceDataType(String sourceDataType) {
            SourceDataType = sourceDataType;
        }

        public String getSourceLossTableType() {
            return SourceLossTableType;
        }

        public void setSourceLossTableType(String sourceLossTableType) {
            SourceLossTableType = sourceLossTableType;
        }

        public String getLossTableType() {
            return LossTableType;
        }

        public void setLossTableType(String lossTableType) {
            LossTableType = lossTableType;
        }

        public String getSourceLossTableBasis() {
            return SourceLossTableBasis;
        }

        public void setSourceLossTableBasis(String sourceLossTableBasis) {
            SourceLossTableBasis = sourceLossTableBasis;
        }

        public Integer getSourceSimulationYears() {
            return SourceSimulationYears;
        }

        public void setSourceSimulationYears(Integer sourceSimulationYears) {
            SourceSimulationYears = sourceSimulationYears;
        }

        public Integer getSourceSimulationPeriodBasis() {
            return SourceSimulationPeriodBasis;
        }

        public void setSourceSimulationPeriodBasis(Integer sourceSimulationPeriodBasis) {
            SourceSimulationPeriodBasis = sourceSimulationPeriodBasis;
        }

        public String getSourceEventSet() {
            return SourceEventSet;
        }

        public void setSourceEventSet(String sourceEventSet) {
            SourceEventSet = sourceEventSet;
        }

        public String getAnalysisCurrency() {
            return AnalysisCurrency;
        }

        public void setAnalysisCurrency(String analysisCurrency) {
            AnalysisCurrency = analysisCurrency;
        }

        public String getFinancialPerspective() {
            return FinancialPerspective;
        }

        public void setFinancialPerspective(String financialPerspective) {
            FinancialPerspective = financialPerspective;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String region) {
            Region = region;
        }

        public String getPeril() {
            return Peril;
        }

        public void setPeril(String peril) {
            Peril = peril;
        }

        public String getRegionPeril() {
            return RegionPeril;
        }

        public void setRegionPeril(String regionPeril) {
            RegionPeril = regionPeril;
        }

        public String getSubPeril() {
            return SubPeril;
        }

        public void setSubPeril(String subPeril) {
            SubPeril = subPeril;
        }

        public String getSubRegion1() {
            return SubRegion1;
        }

        public void setSubRegion1(String subRegion1) {
            SubRegion1 = subRegion1;
        }

        public String getSubRegion2() {
            return SubRegion2;
        }

        public void setSubRegion2(String subRegion2) {
            SubRegion2 = subRegion2;
        }

        public String getSubRegion3() {
            return SubRegion3;
        }

        public void setSubRegion3(String subRegion3) {
            SubRegion3 = subRegion3;
        }

        public String getSubRegion4() {
            return SubRegion4;
        }

        public void setSubRegion4(String subRegion4) {
            SubRegion4 = subRegion4;
        }

        public String getLossAmplification() {
            return LossAmplification;
        }

        public void setLossAmplification(String lossAmplification) {
            LossAmplification = lossAmplification;
        }

        public Boolean getSourceHasEventRemapping() {
            return SourceHasEventRemapping;
        }

        public void setSourceHasEventRemapping(Boolean sourceHasEventRemapping) {
            SourceHasEventRemapping = sourceHasEventRemapping;
        }

        public Boolean getSourceHasGrouping() {
            return SourceHasGrouping;
        }

        public void setSourceHasGrouping(Boolean sourceHasGrouping) {
            SourceHasGrouping = sourceHasGrouping;
        }

        public Integer[] getGroupingParentID() {
            return GroupingParentID;
        }

        public void setGroupingParentID(Integer[] groupingParentID) {
            GroupingParentID = groupingParentID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(InstanceID, ParentID, RootID, CreateDate, UserID, ClientName, ClientID, UWYear, SourceModelProvider, SourceModelSystem, SourceModelSystemlVersion, SourceModelModule, SourceDataType, SourceLossTableType, LossTableType, SourceLossTableBasis, SourceSimulationYears, SourceSimulationPeriodBasis, SourceEventSet, AnalysisCurrency, FinancialPerspective, Region, Peril, RegionPeril, SubPeril, SubRegion1, SubRegion2, SubRegion3, SubRegion4, LossAmplification, SourceHasEventRemapping, SourceHasGrouping, GroupingParentID);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Header other = (Header) obj;
            return Objects.equals(this.InstanceID, other.InstanceID) && Objects.equals(this.ParentID, other.ParentID) && Objects.equals(this.RootID, other.RootID) && Objects.equals(this.CreateDate, other.CreateDate) && Objects.equals(this.UserID, other.UserID) && Objects.equals(this.ClientName, other.ClientName) && Objects.equals(this.ClientID, other.ClientID) && Objects.equals(this.UWYear, other.UWYear) && Objects.equals(this.SourceModelProvider, other.SourceModelProvider) && Objects.equals(this.SourceModelSystem, other.SourceModelSystem) && Objects.equals(this.SourceModelSystemlVersion, other.SourceModelSystemlVersion) && Objects.equals(this.SourceModelModule, other.SourceModelModule) && Objects.equals(this.SourceDataType, other.SourceDataType) && Objects.equals(this.SourceLossTableType, other.SourceLossTableType) && Objects.equals(this.LossTableType, other.LossTableType) && Objects.equals(this.SourceLossTableBasis, other.SourceLossTableBasis) && Objects.equals(this.SourceSimulationYears, other.SourceSimulationYears) && Objects.equals(this.SourceSimulationPeriodBasis, other.SourceSimulationPeriodBasis) && Objects.equals(this.SourceEventSet, other.SourceEventSet) && Objects.equals(this.AnalysisCurrency, other.AnalysisCurrency) && Objects.equals(this.FinancialPerspective, other.FinancialPerspective) && Objects.equals(this.Region, other.Region) && Objects.equals(this.Peril, other.Peril) && Objects.equals(this.RegionPeril, other.RegionPeril) && Objects.equals(this.SubPeril, other.SubPeril) && Objects.equals(this.SubRegion1, other.SubRegion1) && Objects.equals(this.SubRegion2, other.SubRegion2) && Objects.equals(this.SubRegion3, other.SubRegion3) && Objects.equals(this.SubRegion4, other.SubRegion4) && Objects.equals(this.LossAmplification, other.LossAmplification) && Objects.equals(this.SourceHasEventRemapping, other.SourceHasEventRemapping) && Objects.equals(this.SourceHasGrouping, other.SourceHasGrouping) && Objects.deepEquals(this.GroupingParentID, other.GroupingParentID);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Header{");
            sb.append("InstanceID=").append(InstanceID);
            sb.append(", ParentID=").append(ParentID);
            sb.append(", RootID=").append(RootID);
            sb.append(", CreateDate=").append(CreateDate);
            sb.append(", UserID='").append(UserID).append('\'');
            sb.append(", ClientName='").append(ClientName).append('\'');
            sb.append(", ClientID='").append(ClientID).append('\'');
            sb.append(", UWYear=").append(UWYear);
            sb.append(", SourceModelProvider='").append(SourceModelProvider).append('\'');
            sb.append(", SourceModelSystem='").append(SourceModelSystem).append('\'');
            sb.append(", SourceModelSystemlVersion='").append(SourceModelSystemlVersion).append('\'');
            sb.append(", SourceModelModule='").append(SourceModelModule).append('\'');
            sb.append(", SourceDataType='").append(SourceDataType).append('\'');
            sb.append(", SourceLossTableType='").append(SourceLossTableType).append('\'');
            sb.append(", LossTableType='").append(LossTableType).append('\'');
            sb.append(", SourceLossTableBasis='").append(SourceLossTableBasis).append('\'');
            sb.append(", SourceSimulationYears=").append(SourceSimulationYears);
            sb.append(", SourceSimulationPeriodBasis=").append(SourceSimulationPeriodBasis);
            sb.append(", SourceEventSet='").append(SourceEventSet).append('\'');
            sb.append(", AnalysisCurrency='").append(AnalysisCurrency).append('\'');
            sb.append(", FinancialPerspective='").append(FinancialPerspective).append('\'');
            sb.append(", Region='").append(Region).append('\'');
            sb.append(", Peril='").append(Peril).append('\'');
            sb.append(", RegionPeril='").append(RegionPeril).append('\'');
            sb.append(", SubPeril='").append(SubPeril).append('\'');
            sb.append(", SubRegion1='").append(SubRegion1).append('\'');
            sb.append(", SubRegion2='").append(SubRegion2).append('\'');
            sb.append(", SubRegion3='").append(SubRegion3).append('\'');
            sb.append(", SubRegion4='").append(SubRegion4).append('\'');
            sb.append(", LossAmplification='").append(LossAmplification).append('\'');
            sb.append(", SourceHasEventRemapping=").append(SourceHasEventRemapping);
            sb.append(", SourceHasGrouping=").append(SourceHasGrouping);
            sb.append(", GroupingParentID=").append(Arrays.toString(GroupingParentID));
            sb.append('}');
            return sb.toString();
        }
    }

    class MetaData {
        String SourceName;
        String Description;
        String Comment;
        String OriginalCompanyName;
        String OriginalProgram;
        Date SourceImportDate;
        Integer SourceAnalysisID;
        String SourceAnalysisName;
        String SourceAnalysisDescription;
        Date SourceAnalysisRunDate;
        String SourceAnalysisCurrency;
        String SourceAnalysisCurrencyUnit;
        String SourceAnalysisType;
        String SourceAnalysisPeril;
        String SourceAnalysisRegion;
        String SourceAnalysisRegionPeril;
        String SourceAnalysisSubPeril;
        String SourceAnalysisFinancialPerspective;
        String SourceAnalysisLossAmplification;
        Double SourceAnalysisScaleFactor;
        Integer SourceAnalysisExposureID;
        String SourceAnalysisExposureType;
        String SourceAnalysisMode;
        Boolean SourceAnalysisIsGroup;
        String SourceAnalysisEventSet;
        String SourceOtherAnalysisOptionsSettings;

        public MetaData() {
        }

        public MetaData(String sourceName, String description, String comment, String originalCompanyName, String originalProgram, Date sourceImportDate, Integer sourceAnalysisID, String sourceAnalysisName, String sourceAnalysisDescription, Date sourceAnalysisRunDate, String sourceAnalysisCurrency, String sourceAnalysisCurrencyUnit, String sourceAnalysisType, String sourceAnalysisPeril, String sourceAnalysisRegion, String sourceAnalysisRegionPeril, String sourceAnalysisSubPeril, String sourceAnalysisFinancialPerspective, String sourceAnalysisLossAmplification, Double sourceAnalysisScaleFactor, Integer sourceAnalysisExposureID, String sourceAnalysisExposureType, String sourceAnalysisMode, Boolean sourceAnalysisIsGroup, String sourceAnalysisEventSet, String sourceOtherAnalysisOptionsSettings) {
            SourceName = sourceName;
            Description = description;
            Comment = comment;
            OriginalCompanyName = originalCompanyName;
            OriginalProgram = originalProgram;
            SourceImportDate = sourceImportDate;
            SourceAnalysisID = sourceAnalysisID;
            SourceAnalysisName = sourceAnalysisName;
            SourceAnalysisDescription = sourceAnalysisDescription;
            SourceAnalysisRunDate = sourceAnalysisRunDate;
            SourceAnalysisCurrency = sourceAnalysisCurrency;
            SourceAnalysisCurrencyUnit = sourceAnalysisCurrencyUnit;
            SourceAnalysisType = sourceAnalysisType;
            SourceAnalysisPeril = sourceAnalysisPeril;
            SourceAnalysisRegion = sourceAnalysisRegion;
            SourceAnalysisRegionPeril = sourceAnalysisRegionPeril;
            SourceAnalysisSubPeril = sourceAnalysisSubPeril;
            SourceAnalysisFinancialPerspective = sourceAnalysisFinancialPerspective;
            SourceAnalysisLossAmplification = sourceAnalysisLossAmplification;
            SourceAnalysisScaleFactor = sourceAnalysisScaleFactor;
            SourceAnalysisExposureID = sourceAnalysisExposureID;
            SourceAnalysisExposureType = sourceAnalysisExposureType;
            SourceAnalysisMode = sourceAnalysisMode;
            SourceAnalysisIsGroup = sourceAnalysisIsGroup;
            SourceAnalysisEventSet = sourceAnalysisEventSet;
            SourceOtherAnalysisOptionsSettings = sourceOtherAnalysisOptionsSettings;
        }

        public String getSourceName() {
            return SourceName;
        }

        public void setSourceName(String sourceName) {
            SourceName = sourceName;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getOriginalCompanyName() {
            return OriginalCompanyName;
        }

        public void setOriginalCompanyName(String originalCompanyName) {
            OriginalCompanyName = originalCompanyName;
        }

        public String getOriginalProgram() {
            return OriginalProgram;
        }

        public void setOriginalProgram(String originalProgram) {
            OriginalProgram = originalProgram;
        }

        public Date getSourceImportDate() {
            return SourceImportDate;
        }

        public void setSourceImportDate(Date sourceImportDate) {
            SourceImportDate = sourceImportDate;
        }

        public Integer getSourceAnalysisID() {
            return SourceAnalysisID;
        }

        public void setSourceAnalysisID(Integer sourceAnalysisID) {
            SourceAnalysisID = sourceAnalysisID;
        }

        public String getSourceAnalysisName() {
            return SourceAnalysisName;
        }

        public void setSourceAnalysisName(String sourceAnalysisName) {
            SourceAnalysisName = sourceAnalysisName;
        }

        public String getSourceAnalysisDescription() {
            return SourceAnalysisDescription;
        }

        public void setSourceAnalysisDescription(String sourceAnalysisDescription) {
            SourceAnalysisDescription = sourceAnalysisDescription;
        }

        public Date getSourceAnalysisRunDate() {
            return SourceAnalysisRunDate;
        }

        public void setSourceAnalysisRunDate(Date sourceAnalysisRunDate) {
            SourceAnalysisRunDate = sourceAnalysisRunDate;
        }

        public String getSourceAnalysisCurrency() {
            return SourceAnalysisCurrency;
        }

        public void setSourceAnalysisCurrency(String sourceAnalysisCurrency) {
            SourceAnalysisCurrency = sourceAnalysisCurrency;
        }

        public String getSourceAnalysisCurrencyUnit() {
            return SourceAnalysisCurrencyUnit;
        }

        public void setSourceAnalysisCurrencyUnit(String sourceAnalysisCurrencyUnit) {
            SourceAnalysisCurrencyUnit = sourceAnalysisCurrencyUnit;
        }

        public String getSourceAnalysisType() {
            return SourceAnalysisType;
        }

        public void setSourceAnalysisType(String sourceAnalysisType) {
            SourceAnalysisType = sourceAnalysisType;
        }

        public String getSourceAnalysisPeril() {
            return SourceAnalysisPeril;
        }

        public void setSourceAnalysisPeril(String sourceAnalysisPeril) {
            SourceAnalysisPeril = sourceAnalysisPeril;
        }

        public String getSourceAnalysisRegion() {
            return SourceAnalysisRegion;
        }

        public void setSourceAnalysisRegion(String sourceAnalysisRegion) {
            SourceAnalysisRegion = sourceAnalysisRegion;
        }

        public String getSourceAnalysisRegionPeril() {
            return SourceAnalysisRegionPeril;
        }

        public void setSourceAnalysisRegionPeril(String sourceAnalysisRegionPeril) {
            SourceAnalysisRegionPeril = sourceAnalysisRegionPeril;
        }

        public String getSourceAnalysisSubPeril() {
            return SourceAnalysisSubPeril;
        }

        public void setSourceAnalysisSubPeril(String sourceAnalysisSubPeril) {
            SourceAnalysisSubPeril = sourceAnalysisSubPeril;
        }

        public String getSourceAnalysisFinancialPerspective() {
            return SourceAnalysisFinancialPerspective;
        }

        public void setSourceAnalysisFinancialPerspective(String sourceAnalysisFinancialPerspective) {
            SourceAnalysisFinancialPerspective = sourceAnalysisFinancialPerspective;
        }

        public String getSourceAnalysisLossAmplification() {
            return SourceAnalysisLossAmplification;
        }

        public void setSourceAnalysisLossAmplification(String sourceAnalysisLossAmplification) {
            SourceAnalysisLossAmplification = sourceAnalysisLossAmplification;
        }

        public Double getSourceAnalysisScaleFactor() {
            return SourceAnalysisScaleFactor;
        }

        public void setSourceAnalysisScaleFactor(Double sourceAnalysisScaleFactor) {
            SourceAnalysisScaleFactor = sourceAnalysisScaleFactor;
        }

        public Integer getSourceAnalysisExposureID() {
            return SourceAnalysisExposureID;
        }

        public void setSourceAnalysisExposureID(Integer sourceAnalysisExposureID) {
            SourceAnalysisExposureID = sourceAnalysisExposureID;
        }

        public String getSourceAnalysisExposureType() {
            return SourceAnalysisExposureType;
        }

        public void setSourceAnalysisExposureType(String sourceAnalysisExposureType) {
            SourceAnalysisExposureType = sourceAnalysisExposureType;
        }

        public String getSourceAnalysisMode() {
            return SourceAnalysisMode;
        }

        public void setSourceAnalysisMode(String sourceAnalysisMode) {
            SourceAnalysisMode = sourceAnalysisMode;
        }

        public Boolean getSourceAnalysisIsGroup() {
            return SourceAnalysisIsGroup;
        }

        public void setSourceAnalysisIsGroup(Boolean sourceAnalysisIsGroup) {
            SourceAnalysisIsGroup = sourceAnalysisIsGroup;
        }

        public String getSourceAnalysisEventSet() {
            return SourceAnalysisEventSet;
        }

        public void setSourceAnalysisEventSet(String sourceAnalysisEventSet) {
            SourceAnalysisEventSet = sourceAnalysisEventSet;
        }

        public String getSourceOtherAnalysisOptionsSettings() {
            return SourceOtherAnalysisOptionsSettings;
        }

        public void setSourceOtherAnalysisOptionsSettings(String sourceOtherAnalysisOptionsSettings) {
            SourceOtherAnalysisOptionsSettings = sourceOtherAnalysisOptionsSettings;
        }

        @Override
        public int hashCode() {
            return Objects.hash(SourceName, Description, Comment, OriginalCompanyName, OriginalProgram, SourceImportDate, SourceAnalysisID, SourceAnalysisName, SourceAnalysisDescription, SourceAnalysisRunDate, SourceAnalysisCurrency, SourceAnalysisCurrencyUnit, SourceAnalysisType, SourceAnalysisPeril, SourceAnalysisRegion, SourceAnalysisRegionPeril, SourceAnalysisSubPeril, SourceAnalysisFinancialPerspective, SourceAnalysisLossAmplification, SourceAnalysisScaleFactor, SourceAnalysisExposureID, SourceAnalysisExposureType, SourceAnalysisMode, SourceAnalysisIsGroup, SourceAnalysisEventSet, SourceOtherAnalysisOptionsSettings);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final MetaData other = (MetaData) obj;
            return Objects.equals(this.SourceName, other.SourceName) && Objects.equals(this.Description, other.Description) && Objects.equals(this.Comment, other.Comment) && Objects.equals(this.OriginalCompanyName, other.OriginalCompanyName) && Objects.equals(this.OriginalProgram, other.OriginalProgram) && Objects.equals(this.SourceImportDate, other.SourceImportDate) && Objects.equals(this.SourceAnalysisID, other.SourceAnalysisID) && Objects.equals(this.SourceAnalysisName, other.SourceAnalysisName) && Objects.equals(this.SourceAnalysisDescription, other.SourceAnalysisDescription) && Objects.equals(this.SourceAnalysisRunDate, other.SourceAnalysisRunDate) && Objects.equals(this.SourceAnalysisCurrency, other.SourceAnalysisCurrency) && Objects.equals(this.SourceAnalysisCurrencyUnit, other.SourceAnalysisCurrencyUnit) && Objects.equals(this.SourceAnalysisType, other.SourceAnalysisType) && Objects.equals(this.SourceAnalysisPeril, other.SourceAnalysisPeril) && Objects.equals(this.SourceAnalysisRegion, other.SourceAnalysisRegion) && Objects.equals(this.SourceAnalysisRegionPeril, other.SourceAnalysisRegionPeril) && Objects.equals(this.SourceAnalysisSubPeril, other.SourceAnalysisSubPeril) && Objects.equals(this.SourceAnalysisFinancialPerspective, other.SourceAnalysisFinancialPerspective) && Objects.equals(this.SourceAnalysisLossAmplification, other.SourceAnalysisLossAmplification) && Objects.equals(this.SourceAnalysisScaleFactor, other.SourceAnalysisScaleFactor) && Objects.equals(this.SourceAnalysisExposureID, other.SourceAnalysisExposureID) && Objects.equals(this.SourceAnalysisExposureType, other.SourceAnalysisExposureType) && Objects.equals(this.SourceAnalysisMode, other.SourceAnalysisMode) && Objects.equals(this.SourceAnalysisIsGroup, other.SourceAnalysisIsGroup) && Objects.equals(this.SourceAnalysisEventSet, other.SourceAnalysisEventSet) && Objects.equals(this.SourceOtherAnalysisOptionsSettings, other.SourceOtherAnalysisOptionsSettings);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("MetaData{");
            sb.append("SourceName='").append(SourceName).append('\'');
            sb.append(", Description='").append(Description).append('\'');
            sb.append(", Comment='").append(Comment).append('\'');
            sb.append(", OriginalCompanyName='").append(OriginalCompanyName).append('\'');
            sb.append(", OriginalProgram='").append(OriginalProgram).append('\'');
            sb.append(", SourceImportDate=").append(SourceImportDate);
            sb.append(", SourceAnalysisID=").append(SourceAnalysisID);
            sb.append(", SourceAnalysisName='").append(SourceAnalysisName).append('\'');
            sb.append(", SourceAnalysisDescription='").append(SourceAnalysisDescription).append('\'');
            sb.append(", SourceAnalysisRunDate=").append(SourceAnalysisRunDate);
            sb.append(", SourceAnalysisCurrency='").append(SourceAnalysisCurrency).append('\'');
            sb.append(", SourceAnalysisCurrencyUnit='").append(SourceAnalysisCurrencyUnit).append('\'');
            sb.append(", SourceAnalysisType='").append(SourceAnalysisType).append('\'');
            sb.append(", SourceAnalysisPeril='").append(SourceAnalysisPeril).append('\'');
            sb.append(", SourceAnalysisRegion='").append(SourceAnalysisRegion).append('\'');
            sb.append(", SourceAnalysisRegionPeril='").append(SourceAnalysisRegionPeril).append('\'');
            sb.append(", SourceAnalysisSubPeril='").append(SourceAnalysisSubPeril).append('\'');
            sb.append(", SourceAnalysisFinancialPerspective='").append(SourceAnalysisFinancialPerspective).append('\'');
            sb.append(", SourceAnalysisLossAmplification='").append(SourceAnalysisLossAmplification).append('\'');
            sb.append(", SourceAnalysisScaleFactor=").append(SourceAnalysisScaleFactor);
            sb.append(", SourceAnalysisExposureID=").append(SourceAnalysisExposureID);
            sb.append(", SourceAnalysisExposureType='").append(SourceAnalysisExposureType).append('\'');
            sb.append(", SourceAnalysisMode='").append(SourceAnalysisMode).append('\'');
            sb.append(", SourceAnalysisIsGroup=").append(SourceAnalysisIsGroup);
            sb.append(", SourceAnalysisEventSet='").append(SourceAnalysisEventSet).append('\'');
            sb.append(", SourceOtherAnalysisOptionsSettings='").append(SourceOtherAnalysisOptionsSettings).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
