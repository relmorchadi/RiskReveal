package com.scor.rr.domain.entities.exposure;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by U002629 on 02/04/2015.
 */

@Entity
@Table(name = "ExposureViewVersions")
@Data
public class ExposureViewVersion{

    @Id
    private String id;
    private String name;
    private Integer number;
    private Boolean current;
    @OneToOne(fetch = FetchType.LAZY)
    private ExposureViewDefinition definition;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ExposureViewExtractQuery> queries;

    public ExposureViewVersion() {
    }

    public void buildReferencesFromIds(Map<String, String> idMap) {
        definition = new ExposureViewDefinition();
        definition.setId(idMap.get(ExposureViewDefinition.class.getSimpleName()));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExposureViewVersion{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", number=").append(number);
        sb.append(", current=").append(current);
        sb.append(", queries=").append(queries);
        sb.append('}');
        return sb.toString();
    }
}
