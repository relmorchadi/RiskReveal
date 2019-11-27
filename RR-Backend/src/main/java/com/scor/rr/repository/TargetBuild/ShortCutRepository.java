package com.scor.rr.repository.TargetBuild;

import com.scor.rr.domain.TargetBuild.Search.ShortCut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortCutRepository extends JpaRepository<ShortCut, Long> {
}
