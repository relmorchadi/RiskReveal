package com.scor.rr.domain.importfile;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "RefFileBasedImport", schema = "dbo")
@Data
@NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode
public class RefFileBasedImportEntity {
    public static final String MODEL_PROVIDER_COLUMN = "ModelProvider";
    public static final String MODEL_SYSTEM_COLUMN = "ModelSystem";
    public static final String PERIL_COLUMN = "Peril";
    public static final String REGION_NAME_COLUMN = "RegionName";
    public static final String COUNTRY_COLUMN = "Country";
    public static final String EVENT_SET_ID_COLUMN = "EventSetId";

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "country1")
    private String country1;
    @Column(name = "country2")
    private String country2;
    @Column(name = "country3")
    private String country3;
    @Column(name = "eventSetId")
    private Integer eventSetId;
    @Column(name = "modelName")
    private String modelName;
    @Column(name = "modelVersionYear")
    private int modelVersionYear;
    @Column(name = "modellingSystem")
    private String modellingSystem;
    @Column(name = "modellingVendor")
    private String modellingVendor;
    @Column(name = "peqtFile")
    private String peqtFile;
    @Column(name = "peril")
    private String peril;
    @Column(name = "regionName")
    private String regionName;

}
