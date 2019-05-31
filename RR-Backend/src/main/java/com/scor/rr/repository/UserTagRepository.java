package com.scor.rr.repository;

import com.scor.rr.domain.UserTag;
import com.scor.rr.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserTagRepository extends JpaRepository<UserTag, Integer>{
    List<UserTag> findByWorkspace(Workspace workspace);
    Optional<UserTag> findByTagName(String tagName);
}
