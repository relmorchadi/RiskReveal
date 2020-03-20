package com.scor.rr.domain.Response.ScopeAndCompleteness;

import com.scor.rr.domain.PltHeaderEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttachedPLTsInfo {

    private String contractSectionId;
    private PltHeaderEntity attachedPLT;
}
