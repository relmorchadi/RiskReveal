package com.scor.rr.repository;

import com.scor.rr.entity.InuringContractNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringContractNodeRepository extends JpaRepository<InuringContractNode, Integer> {

    List<InuringContractNode> findByInuringPackageId(int inuringPackageId);

    InuringContractNode findByInuringContractNodeId(int inuringContractNodeId);

}
