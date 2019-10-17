package com.scor.rr.domain.importfile;

import javax.persistence.*;

@Entity
@Table(name = "PEQTFileType", schema = "dbo", catalog = "RiskReveal")
public class PEQTFileType {

    private String id;
    private String modelProvider;
    private String modelSystem;
    private String modelSystemVersion;
    private String modelSystemInstance;
    private String fileType;
    private String delim;
    private boolean hasHeader;

    @Id
    @Column(name = "PEQTFileTypeId", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ModelProvider", nullable = true, length = 200)
    public String getModelProvider() {
        return modelProvider;
    }

    public void setModelProvider(String modelProvider) {
        this.modelProvider = modelProvider;
    }

    @Basic
    @Column(name = "ModelSystem", nullable = true, length = 200)
    public String getModelSystem() {
        return modelSystem;
    }

    public void setModelSystem(String modelSystem) {
        this.modelSystem = modelSystem;
    }

    @Basic
    @Column(name = "ModelSystemVersion", nullable = true, length = 200)
    public String getModelSystemVersion() {
        return modelSystemVersion;
    }

    public void setModelSystemVersion(String modelSystemVersion) {
        this.modelSystemVersion = modelSystemVersion;
    }

    @Basic
    @Column(name = "ModelSystemInstance", nullable = true, length = 200)
    public String getModelSystemInstance() {
        return modelSystemInstance;
    }

    public void setModelSystemInstance(String modelSystemInstance) {
        this.modelSystemInstance = modelSystemInstance;
    }

    @Basic
    @Column(name = "FileType", nullable = true, length = 200)
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "Delim", nullable = true, length = 200)
    public String getDelim() {
        return delim;
    }

    public void setDelim(String delim) {
        this.delim = delim;
    }

    @Basic
    @Column(name = "HasHeader", nullable = true)
    public boolean isHasHeader() {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public PEQTFileType(String modelProvider, String modelSystem, String modelSystemVersion, String modelSystemInstance, String fileType, String delim, boolean hasHeader) {
        this.modelProvider = modelProvider;
        this.modelSystem = modelSystem;
        this.modelSystemVersion = modelSystemVersion;
        this.modelSystemInstance = modelSystemInstance;
        this.fileType = fileType;
        this.delim = delim;
        this.hasHeader = hasHeader;
        setId(modelProvider.replaceAll(" ", "") + "_" +
                modelSystem.replaceAll(" ", "") + "_" +
                modelSystemVersion.replaceAll(" ", "") + "_" +
                modelSystemInstance.replaceAll(" ", ""));
    }


}
