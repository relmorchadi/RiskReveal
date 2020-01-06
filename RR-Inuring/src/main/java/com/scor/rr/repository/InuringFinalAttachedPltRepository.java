package com.scor.rr.repository;

import com.scor.rr.entity.InuringFinalAttachedPLT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InuringFinalAttachedPltRepository extends JpaRepository<InuringFinalAttachedPLT, Long> {

    InuringFinalAttachedPLT findByInuringFinalAttachedPLT(long inuringFinalAttachedPlt);
    void deleteAllByInuringFinalNodeId(long inuringFinalNodeId);
    List<InuringFinalAttachedPLT> findAllByInuringFinalNodeId(long inuringFinalNodeId);
}
