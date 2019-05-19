package com.scor.rr.repository;

import com.scor.rr.domain.EdmRdm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdmRdmRepository extends JpaRepository<EdmRdm, Integer> {

    public Page<EdmRdm> findByNameLike(String name, Pageable pageable);

}
