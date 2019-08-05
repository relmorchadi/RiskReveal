package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ENTITY", schema = "dbo", catalog = "RiskReveal")
public class EntityEntity {
    private int idEntity;
    private String entityCode;
    private String entityDescription;

    @Id
    @Column(name = "id_entity", nullable = false)
    public int getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    @Basic
    @Column(name = "entity_code", length = 50)
    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    @Basic
    @Column(name = "entity_description", length = 200)
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
        return idEntity == that.idEntity &&
                Objects.equals(entityCode, that.entityCode) &&
                Objects.equals(entityDescription, that.entityDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEntity, entityCode, entityDescription);
    }
}
