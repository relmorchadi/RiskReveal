package com.scor.rr.domain.entities.exposure;

import com.scor.rr.domain.entities.plt.ModellingSystemVersion;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by U002629 on 02/04/2015.
 */
@Entity
@Table(name = "ExposureViewExtractQueries")
@Data
//@CompoundIndexes({
//        @CompoundIndex(name = "versionRefIx", def = "{'modellingSystemVersion' : 1}"),
//        @CompoundIndex(name = "exposureRefIx", def = "{'exposureViewVersion' : 1}")
//})
public class ExposureViewExtractQuery {

    @Id
    private String id;
    @Column(name = "query")
    private String query;
    @OneToOne(fetch = FetchType.LAZY)
    private ExposureViewVersion exposureViewVersion;
    @OneToOne
    private ModellingSystemVersion modellingSystemVersion;

    @Transient
    private Map<Integer, String> parameters;
    @Transient
    private Map<String, String> placeHolders;

    public ExposureViewExtractQuery() {
        placeHolders = new HashMap<String, String>();
        parameters = new TreeMap<Integer, String>();
    }


    public void buildReferencesFromIds(Map<String, String> idMap) {
        modellingSystemVersion = new ModellingSystemVersion();
        modellingSystemVersion.setId(idMap.get(ModellingSystemVersion.class.getSimpleName()));
        exposureViewVersion = new ExposureViewVersion();
        exposureViewVersion.setId(idMap.get(ExposureViewVersion.class.getSimpleName()));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExposureSummaryExtractQuery{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", modellingSystemVersion=").append(modellingSystemVersion);
        sb.append(", query='").append(query).append('\'');
        sb.append(", placeHolders=").append(placeHolders);
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
