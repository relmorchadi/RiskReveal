package com.scor.rr.repository;

import com.scor.rr.entity.InuringContractLayerReinstatementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringContractLayerReinstatementDetailRepository extends JpaRepository<InuringContractLayerReinstatementDetail, Integer> {

    void deleteByInuringContractLayerReinstatementDetailId(int inuringContractLayerReinstatementDetailId);
    List<InuringContractLayerReinstatementDetail> findByInuringContractLayerId(int inuringContractLayerId);
}
