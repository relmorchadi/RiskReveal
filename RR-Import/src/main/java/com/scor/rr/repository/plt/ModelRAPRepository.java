package com.scor.rr.repository.plt;

import com.scor.rr.domain.entities.plt.ModelRAP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ModelRAP Repository
 *
 * @author HADDINI Zakariyae
 */
public interface ModelRAPRepository extends JpaRepository<ModelRAP, String> {

    @Query(value = "select m from ModelRAP m where m.modelRAPSource.id = :modelRAPSourceId and m.regionPerilDefault = true")
    ModelRAP findByModelRAPSourceIdAndRegionPerilDefaultTrue(@Param("modelRAPSourceId") String modelRAPSourceId);

}