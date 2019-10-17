package com.scor.rr.domain.entities.cat.meta;

import com.scor.rr.domain.entities.omega.Section;
import com.scor.rr.domain.entities.plt.ScorPLTHeader;
import com.scor.rr.domain.entities.references.RegionPeril;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Hamiani Mohammed
 * creation date  18/09/2019 at 16:18
 **/
@Data
@Entity
public class CatAnalysisDefinition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private RegionPeril regionPeril;

    // NOTA : does this come from ExposureSummaryDetails ?
    private String grainType;

    // NOTA : enable selection of specific grains, to be discussed further
    @Transient
    private List<String> selectedGrains;

    @Transient
    private List<Section> sections;

    @OneToOne
    private ScorPLTHeader pltHeader;

    private Boolean pltSelected;

    private Date modificationDate;

    private String comment;

    public List<String> getSelectedGrains() {
        if (selectedGrains == null) {
            selectedGrains = new ArrayList<>();
        }
        return selectedGrains;
    }
}
