package com.scor.rr.entity;


import com.scor.rr.enums.InuringElementType;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Entity
@Data
@Table(name = "InuringFilterCriteria", schema = "dbo", catalog = "RiskReveal")
public class InuringFilterCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringFilterCriteriaId", nullable = false)
    private long inuringFilterCriteriaId;

    @Column(name = "Entity")
    private int entity;

    @Column(name = "InuringObjectType")
    private InuringElementType inuringObjectType;

    @Column(name = "InuringObjectId")
    private long inuringObjectId;

    @Column(name = "FilterKey")
    private String filterKey;

    @Column(name = "FilterValue")
    private String filterValue;

    @Column(name = "Including")
    private boolean including;

    public InuringFilterCriteria() {
    }

    public InuringFilterCriteria(InuringElementType inuringObjectType, long inuringObjectId, String filterKey, String filterValue, boolean including) {
        this.entity = 1;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.including = including;
    }
}
