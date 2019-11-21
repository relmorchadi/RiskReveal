package com.scor.rr.repository;

import com.scor.rr.entity.InuringFinalNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by u004602 on 16/09/2019.
 */
@Repository
public interface InuringFinalNodeRepository extends JpaRepository<InuringFinalNode, Long> {
    InuringFinalNode findByInuringFinalNodeId(long inuringFinalNodeId);
    InuringFinalNode findByInuringPackageId(long inuringPackageId);
    void deleteByInuringPackageId(long inuringPackageId);
}
