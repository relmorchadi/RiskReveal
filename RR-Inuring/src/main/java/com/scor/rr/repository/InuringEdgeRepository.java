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
public interface InuringEdgeRepository extends JpaRepository<InuringEdge, Long> {
    List<InuringEdge> findByInuringPackageId(long inuringPackageId);
    InuringEdge findByInuringEdgeId(long inuringEdgeId);
    void deleteByInuringEdgeId(long inuringEdgeId);
    void deleteBySourceNodeTypeAndSourceNodeId(InuringNodeType nodeType, long nodeId);
    void deleteByTargetNodeTypeAndTargetNodeId(InuringNodeType nodeType, long nodeId);

    List<InuringEdge> findAllBySourceNodeIdAndSourceNodeType(long sourceNodeId, InuringNodeType sourceNodeType);
    List<InuringEdge> findAllByTargetNodeIdAndTargetNodeType(long targetNodeId, InuringNodeType targetNodeType);

}
