package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.plt.ModelRAPSource;
import com.scor.rr.domain.entities.plt.ModellingSystem;
import com.scor.rr.domain.entities.plt.ModellingSystemVersion;
import com.scor.rr.domain.entities.plt.ModellingVendor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The persistent class for the ModelRAPSourceMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ModelRAPSourceMapping")
@Data
public class ModelRAPSourceMapping {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ModelRAPSourceMappingId")
    private String modelRAPSourceMappingId;
    @Column(name = "DlmProfileName")
    private String dlmProfileName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModelRAPSourceId")
    private ModelRAPSource target;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemId")
    private ModellingSystem modellingSystem;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingVendorId")
    private ModellingVendor modellingVendor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ModellingSystemVersionId")
    private ModellingSystemVersion modellingSystemVersion;


}
