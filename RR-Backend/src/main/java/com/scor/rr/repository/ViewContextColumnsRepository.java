package com.scor.rr.repository;

import com.scor.rr.domain.entities.ViewContextColumns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ViewContextColumnsRepository  extends JpaRepository<ViewContextColumns, Long> {

    @Query(value = "EXEC dbonew.usp_ViewContextUpdateColumnWidth @UserCode =:UserCode, @ViewContextColumnId =:ViewContextColumnId, @Width =:Width", nativeQuery = true)
    List<Map<String, Object>> updateColumnWidth(@Param("UserCode") String userCode, @Param("ViewContextColumnId") Long viewContextColumnId, @Param("Width") Integer width);
}
