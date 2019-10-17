package com.scor.rr.domain.entities.exposure;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "ExposureViewDefinitions")
@Data
public class ExposureViewDefinition {

    @Id
    private String id;
    private String name;
    private Integer orderNb;
    @OneToMany(fetch = FetchType.LAZY)
    private List<ExposureViewVersion> versions;
    //    @OneToMany(fetch = FetchType.LAZY)
//    private List<ExposureGlobalViewRepository> views;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExposureViewDefinition{");
        sb.append("id='").append(getId()).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", versions=").append(versions);
        sb.append('}');
        return sb.toString();
    }
}
