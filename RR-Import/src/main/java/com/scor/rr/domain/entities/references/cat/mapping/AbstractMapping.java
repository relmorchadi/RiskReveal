package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.plt.ModellingSystem;
import com.scor.rr.domain.entities.plt.ModellingSystemVersion;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

/**
 * The persistent class for the AbstractMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AbstractMapping")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class AbstractMapping<T> {
    @Id
    @Column(name = "MappingId")
    private String mappingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TargetValueId")
    private T targetValue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemId")
    private ModellingSystem modellingSystem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemVersionId")
    private ModellingSystemVersion modellingSystemVersion;

    public AbstractMapping() {
    }

    public AbstractMapping(String id, ModellingSystemVersion modellingSystemVersion, ModellingSystem modellingSystem, T targetValue) {
        this.mappingId = id;
        this.modellingSystemVersion = modellingSystemVersion;
        this.modellingSystem = modellingSystem;
        this.targetValue = targetValue;
    }

    public void buildReferencesFromIds(Map<String, String> idMap) {
        modellingSystemVersion = new ModellingSystemVersion();
        modellingSystemVersion.setId(idMap.get(ModellingSystemVersion.class.getSimpleName()));
        modellingSystem = new ModellingSystem();
        modellingSystem.setId(idMap.get(ModellingSystem.class.getSimpleName()));
    }
}
