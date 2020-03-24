package com.scor.rr.repository;

import com.scor.rr.domain.entities.UserWorkspaceTabs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserWorkspaceTabsRepository extends JpaRepository<UserWorkspaceTabs, Long> {
    List<UserWorkspaceTabs> findAllByUserCodeOrderByTabOrderAsc(String userCode);
    Optional<UserWorkspaceTabs> findByUserCodeAndWorkspaceContextCodeAndWorkspaceUwYear(String userCode, String workspaceContextCode, Integer workspaceUwYear);
    Optional<UserWorkspaceTabs> findBySelected(Boolean selected);
}
