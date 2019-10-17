package com.scor.rr.domain.entities.references;

import com.scor.rr.domain.entities.plt.ModellingSystem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceCountryPerilRegionPerilMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String sourceCountryCode2;
    @Column
    private String sourceCountryCode3;
    @Column
    private String sourcePerilCode;
    @OneToOne
    private RegionPeril regionPeril;
    @OneToOne
    private ModellingSystem modellingSystem;
}
