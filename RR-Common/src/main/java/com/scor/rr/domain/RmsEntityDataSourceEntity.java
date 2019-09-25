package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RmsEntityDataSource", schema = "dbo", catalog = "RiskReveal")
public class RmsEntityDataSourceEntity {
    private int rmsEntityDataSourceId;
    private String source;
    private String rmsId;
    private String instanceName;
    private String instanceId;
    private String name;
    private String type;
    private Integer versionId;
    private Timestamp dateCreated;

    @Id
    @Column(name = "rmsEntityDataSourceId", nullable = false)
    public int getRmsEntityDataSourceId() {
        return rmsEntityDataSourceId;
    }

    public void setRmsEntityDataSourceId(int rmsEntityDataSourceId) {
        this.rmsEntityDataSourceId = rmsEntityDataSourceId;
    }

    @Basic
    @Column(name = "source", length = 255)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "rmsId", length = 255)
    public String getRmsId() {
        return rmsId;
    }

    public void setRmsId(String rmsId) {
        this.rmsId = rmsId;
    }

    @Basic
    @Column(name = "instanceName", length = 255)
    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    @Basic
    @Column(name = "instanceId", length = 255)
    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @Basic
    @Column(name = "name", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type", length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "versionId")
    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    @Basic
    @Column(name = "dateCreated")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RmsEntityDataSourceEntity that = (RmsEntityDataSourceEntity) o;
        return rmsEntityDataSourceId == that.rmsEntityDataSourceId &&
                Objects.equals(source, that.source) &&
                Objects.equals(rmsId, that.rmsId) &&
                Objects.equals(instanceName, that.instanceName) &&
                Objects.equals(instanceId, that.instanceId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(versionId, that.versionId) &&
                Objects.equals(dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rmsEntityDataSourceId, source, rmsId, instanceName, instanceId, name, type, versionId, dateCreated);
    }
}
