package com.scor.rr.repository;

import com.scor.rr.domain.entities.ViewContextColumns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ViewContextColumnsRepository  extends JpaRepository<ViewContextColumns, Long> {

    @Transactional
    @Procedure(procedureName = "dbonew.usp_ViewContextUpdateColumnWidth")
    void updateColumnWidth(@Param("UserCode") String userCode, @Param("ViewContextColumnId") Long viewContextColumnId, @Param("Width") Integer width);
}
