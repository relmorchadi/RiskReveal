package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ENTITY", schema = "dbo", catalog = "RiskReveal")
public class EntityEntity {
    private int entityId;
    private String entityCode;
    private String entityDescription;

    @Id
    @Column(name = "EntityId", nullable = false)
    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Basic
    @Column(name = "EntityCode", length = 50)
    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    @Basic
    @Column(name = "EntityDescription", length = 200)
    public String getEntityDescription() {
        return entityDescription;
    }

    public void setEntityDescription(String entityDescription) {
        this.entityDescription = entityDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityEntity that = (EntityEntity) o;
        return entityId == that.entityId &&
                Objects.equals(entityCode, that.entityCode) &&
                Objects.equals(entityDescription, that.entityDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, entityCode, entityDescription);
    }
}
