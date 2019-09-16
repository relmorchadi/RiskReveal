package com.scor.rr.repository;

import com.scor.rr.entity.InuringFinalNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by u004602 on 16/09/2019.
 */
public interface InuringFinalNodeRepository extends JpaRepository<InuringFinalNode, Integer> {
    InuringFinalNode findByInuringPackageId(int inuringPackageId);
}
