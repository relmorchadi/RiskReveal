package com.scor.rr.domain.dto.TargetBuild;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PLTHeaderDeleteRequest {
    List<Long> pltHeaderIds;
    String deletedBy;
    Date deletedOn;
    String deletedDue;
}
