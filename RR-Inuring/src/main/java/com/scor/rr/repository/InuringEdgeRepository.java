package com.scor.rr.repository;

import com.scor.rr.entity.InuringEdge;
import com.scor.rr.enums.InuringNodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u004602 on 13/09/2019.
 */
@Repository
public interface InuringEdgeRepository extends JpaRepository<InuringEdge, Integer> {
    List<InuringEdge> findByInuringPackageId(int inuringPackageId);
    InuringEdge findByInuringEdgeId(int inuringEdgeId);
    void deleteByInuringEdgeId(int inuringEdgeId);
    void deleteBySourceNodeTypeAndSourceNodeId(InuringNodeType nodeType, int nodeId);
    void deleteByTargetNodeTypeAndTargetNodeId(InuringNodeType nodeType, int nodeId);

    List<InuringEdge> findAllBySourceNodeIdAndSourceNodeType(int sourceNodeId, InuringNodeType sourceNodeType);
    List<InuringEdge> findAllByTargetNodeIdAndTargetNodeType(int targetNodeId, InuringNodeType targetNodeType);

}
