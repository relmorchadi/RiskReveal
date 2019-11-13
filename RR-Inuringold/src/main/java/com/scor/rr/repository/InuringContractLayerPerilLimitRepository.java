package com.scor.rr.repository;

import com.scor.rr.entity.InuringContractLayerPerilLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringContractLayerPerilLimitRepository extends JpaRepository<InuringContractLayerPerilLimit, Integer> {

    void deleteByInuringContractLayerPerilLimitId(int inuringContractLayerPerilLimitId);
    List<InuringContractLayerPerilLimit> findByInuringContractLayerId(int inuringContractLayerId);
}
