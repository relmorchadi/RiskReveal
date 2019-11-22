package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExposureViewQuery {

    @Id
    private Long ExposureViewQueryId;
    @Column(name = "Query")
    private String query;

    // TODO : Not complete yet

    @OneToOne
    @JoinColumn(name = "ExposureViewVersionId")
    private ExposureViewVersion exposureViewVersion;
}
