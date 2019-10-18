package com.scor.rr.repository;

import com.scor.rr.entity.InuringContractLayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringContractLayerRepository extends JpaRepository<InuringContractLayer, Integer> {

    void deleteByInuringContractLayerId(int contractLayerId);
    InuringContractLayer findByInuringContractLayerId(int inuringContractLayerId);

    List<InuringContractLayer> findByInuringContractNodeId(int inuringContractNodeId);

    @Query(value="UPDATE InuringContractLayer SET LayerNumber = LayerNumber - 1 WHERE LayerNumber > ?1 AND InuringContractNodeId = ?2",nativeQuery = true)
    void reorderTheLayers(int layerNumber,int contractNodeId);


}
