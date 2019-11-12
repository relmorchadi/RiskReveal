package com.scor.rr.domain.dto.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkspaceCount {
    Integer favorite;
    Integer recent;
    Integer assigned;
    Integer pinned;
}
