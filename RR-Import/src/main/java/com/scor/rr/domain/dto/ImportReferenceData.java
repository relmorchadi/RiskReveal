package com.scor.rr.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ImportReferenceData {

    List<Map<String,String>> financialPerspectives;
    List<RmsInstanceDto> rmsInstances;
    String[] currencies;
    List<CARDivisionDto> division;

}
