package com.scor.rr.repository;

import com.scor.rr.entity.InuringInputNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u004602 on 10/09/2019.
 */
@Repository
public interface InuringInputNodeRepository extends JpaRepository<InuringInputNode, Long> {
    InuringInputNode findByInuringInputNodeId(long inuringInputNodeId);
    List<InuringInputNode> findByInuringPackageId(long inuringPackageId);
    void deleteByInuringInputNodeId(long inuringInputNodeId);
}
