package com.scor.rr.repository;


import com.scor.rr.domain.PltHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PltHeaderRepository extends JpaRepository<PltHeader,String>{

    List<PltHeader> findPltHeadersByIdIn(List<String> listIds);
    List<PltHeader> findPltHeadersByIdInAndIdNotIn(List<String> listIds,List<String> notIds);

}
