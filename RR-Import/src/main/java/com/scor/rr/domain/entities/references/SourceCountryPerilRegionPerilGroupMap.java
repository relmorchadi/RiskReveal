package com.scor.rr.domain.entities.references;

import com.scor.rr.domain.entities.plt.ModellingSystem;
import com.scor.rr.domain.entities.plt.ModellingSystemVersion;
import com.scor.rr.domain.entities.references.cat.mapping.AbstractRegionPerilMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Map;

@Entity
public class SourceCountryPerilRegionPerilGroupMap extends AbstractRegionPerilMapping<RegionPerilGroup> {

    @Column
    private String sourceCountryCode2;
    @Column
    private String sourceCountryCode3;

    public SourceCountryPerilRegionPerilGroupMap(String id, ModellingSystemVersion modellingSystemVersion, ModellingSystem modellingSystem, String sourceCountryCode, String sourceCountryCode2, String sourceCountryCode3, String sourcePerilCode, RegionPerilGroup targetValue) {
        super(id, modellingSystemVersion, modellingSystem, sourceCountryCode, sourcePerilCode, targetValue);
        this.sourceCountryCode2 = sourceCountryCode2;
        this.sourceCountryCode3 = sourceCountryCode3;
    }

    @Override
    public void buildReferencesFromIds(Map<String, String> idMap) {
        super.buildReferencesFromIds(idMap);
        setTargetValue(new RegionPerilGroup());
        getTargetValue().setId(idMap.get(RegionPerilGroup.class.getSimpleName()));
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SourceCountryPerilRegionPerilGroupMap{");
        sb.append(super.toString());
        sb.append(", sourceCountryCode2='").append(sourceCountryCode2).append('\'');
        sb.append(", sourceCountryCode3='").append(sourceCountryCode3).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

