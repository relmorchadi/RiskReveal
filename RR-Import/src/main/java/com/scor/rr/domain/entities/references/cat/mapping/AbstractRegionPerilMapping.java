package com.scor.rr.domain.entities.references.cat.mapping;

import com.scor.rr.domain.entities.plt.ModellingSystem;
import com.scor.rr.domain.entities.plt.ModellingSystemVersion;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * The persistent class for the AbstractRegionPerilMapping database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "AbstractRegionPerilMapping")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class AbstractRegionPerilMapping<T> extends AbstractMapping<T> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    @Column(name = "SourcePerilCode")
    private String sourcePerilCode;
    @Column(name = "SourceCountryCode")
    private String sourceCountryCode;

    public AbstractRegionPerilMapping(String id, ModellingSystemVersion modellingSystemVersion, ModellingSystem modellingSystem, String sourceCountryCode, String sourcePerilCode, T targetValue) {
        super(id, modellingSystemVersion, modellingSystem, targetValue);
        this.sourceCountryCode = sourceCountryCode;
        this.sourcePerilCode = sourcePerilCode;
    }
    public AbstractRegionPerilMapping(){
        super();
    }
}
