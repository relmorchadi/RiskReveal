package com.scor.rr.domain.entities.cat;

import com.scor.rr.domain.entities.references.cat.GlobalViewSummary;
import com.scor.rr.domain.entities.references.omega.PeriodBasis;
import lombok.Data;

import javax.persistence.*;
import java.util.Map;

/**
 * The persistent class for the GlobalExposureView database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "GlobalExposureView")
@Data
public class GlobalExposureView {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GlobalExposureViewId")
    private String globalExposureViewId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Version")
    private Integer version;
    @Column(name = "DivisionNumber")
    private Integer divisionNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExposureDataId")
    private File exposureData;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATRequestId")
    private CATRequest catRequest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PeriodBasisId")
    private PeriodBasis periodBasis;

    @Transient
    private Map<Integer, GlobalViewSummary> globalViewSummariesByName;

    public static String buildId(String carId, String division, String periodBasis, String dsName) {
        return carId + "_" + division + "_" + periodBasis + "_" + dsName;
    }

    public GlobalExposureView(String carID, Integer divisionNumber, PeriodBasis periodBasis, Integer version, String name, File exposureData, Map<Integer, GlobalViewSummary> globalViewSummariesByName) {
        globalExposureViewId = buildId(carID, String.valueOf(divisionNumber), periodBasis.getPeriodBasisId(), name);
        catRequest = new CATRequest();
        this.catRequest.setCatRequestId(carID);
        this.divisionNumber = divisionNumber;
        this.periodBasis = periodBasis;
        this.version = version;
        this.name = name;
        this.exposureData = exposureData;
        this.globalViewSummariesByName = globalViewSummariesByName;
    }

    public GlobalExposureView(String carID, Integer divisionNumber, String periodBasis, Integer version, String name, File exposureData, Map<Integer, GlobalViewSummary> globalViewSummariesByName) {
        globalExposureViewId = buildId(carID, String.valueOf(divisionNumber), periodBasis, name);
        catRequest = new CATRequest();
        this.catRequest.setCatRequestId(carID);
        this.divisionNumber = divisionNumber;
        this.version = version;
        this.name = name;
        this.exposureData = exposureData;
        this.globalViewSummariesByName = globalViewSummariesByName;
    }
}
