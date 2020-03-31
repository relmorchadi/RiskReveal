package com.scor.rr.domain.dto;

import com.scor.rr.domain.entities.UserWorkspaceTabs;
import lombok.Data;

import java.util.List;

@Data
public class UserWorkspaceTabsCloseRequest {
    List<UserWorkspaceTabs> tabs;
    UserWorkspaceTabs toClose;
}
