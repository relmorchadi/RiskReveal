package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MetadataHeaderSegment", schema = "dbo", catalog = "RiskReveal")
public class MetadataHeaderSegmentEntity {
    private int metadataHeaderSegmentId;
    private String metadataAttribute;
    private String description;

    @Id
    @Basic
    @Column(name = "MetadataHeaderSegmentId", nullable = false)
    public int getMetadataHeaderSegmentId() {
        return metadataHeaderSegmentId;
    }

    public void setMetadataHeaderSegmentId(int metadataHeaderSegmentId) {
        this.metadataHeaderSegmentId = metadataHeaderSegmentId;
    }

    @Basic
    @Column(name = "MetadataAttribute", nullable = true, length = 200)
    public String getMetadataAttribute() {
        return metadataAttribute;
    }

    public void setMetadataAttribute(String metadataAttribute) {
        this.metadataAttribute = metadataAttribute;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataHeaderSegmentEntity that = (MetadataHeaderSegmentEntity) o;
        return metadataHeaderSegmentId == that.metadataHeaderSegmentId &&
                Objects.equals(metadataAttribute, that.metadataAttribute) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metadataHeaderSegmentId, metadataAttribute, description);
    }
}
