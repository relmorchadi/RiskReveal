package com.scor.rr.repository;

import com.scor.rr.entity.InuringInputAttachedPLT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by u004602 on 10/09/2019.
 */
@Repository
public interface InuringInputAttachedPLTRepository extends JpaRepository<InuringInputAttachedPLT, Long> {
    InuringInputAttachedPLT findByInuringInputAttachedPLTId(long inuringInputAttachedPLT);
    List<InuringInputAttachedPLT> findByInuringInputNodeId(long inuringInputNodeId);
    void deleteByInuringInputNodeId(long inuringInputNodeId);
    void deleteByInuringInputAttachedPLTId(long inuringInputAttachedPLT);
}
