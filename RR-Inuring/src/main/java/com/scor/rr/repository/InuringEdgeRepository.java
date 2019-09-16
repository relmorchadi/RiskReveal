package com.scor.rr.repository;

import com.scor.rr.entity.InuringEdge;
import com.scor.rr.entity.InuringInputNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by u004602 on 13/09/2019.
 */
public interface InuringEdgeRepository extends JpaRepository<InuringEdge, Integer> {
    List<InuringEdge> findByInuringPackageId(int inuringPackageId);
}
