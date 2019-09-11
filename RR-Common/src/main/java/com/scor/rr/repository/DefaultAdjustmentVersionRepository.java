package com.scor.rr.repository;

import com.scor.rr.domain.DefaultAdjustmentVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DefaultAdjustmentVersionRepository extends JpaRepository<DefaultAdjustmentVersionEntity,Integer> {

}
