package com.scor.rr.repository;

import com.scor.rr.entity.InuringContractLayerParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringContractLayerParamRepository extends JpaRepository<InuringContractLayerParam, Long> {

    List<InuringContractLayerParam> findByInuringContractLayerId(long inuringContractLayerId);
}
