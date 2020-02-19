package com.scor.rr.repository;

import com.scor.rr.domain.entities.UserWorkspaceTabs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWorkspaceTabsRepository extends JpaRepository<UserWorkspaceTabs, Long> {
    List<UserWorkspaceTabs> findAllByUserCodeOrdOrderByOpenedDateDesc(String userCode);
}
