package com.scor.rr.repository;

import com.scor.rr.entity.InuringCanvasLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Soufiane Izend on 01/10/2019.
 */

@Repository
public interface InuringCanvasLayoutRepository extends JpaRepository<InuringCanvasLayout, Long> {

    List<InuringCanvasLayout> findByInuringPackageId(long inuringPackageId);
}
