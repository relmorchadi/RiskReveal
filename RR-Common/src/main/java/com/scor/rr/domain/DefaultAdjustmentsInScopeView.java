package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "vw_DefaultAdjustmentsInScope")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultAdjustmentsInScopeView {

    private String workspaceContextCode;
    private Integer uwYear;

    @Id
    private Long pltId;

    private String pltType;

    @OneToOne
    @JoinColumn(name = "AdjustmentNodeId")
    private AdjustmentNode adjustmentNode;

    private String basicCode;

    private String basicShortName;

    private String categoryCode;

    private String adjustmentType;

    private Boolean capped;

    public Object getAdjustments() {
        return adjustmentType.equals("Linear") ? adjustmentNode.getLinearAdjustment() : adjustmentNode.getNonLinearAdjustment();
    }


}
