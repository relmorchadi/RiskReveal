package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportParamsDto {

    private String batchJobName;
    private String catReqId;
    private String edm;
    private String rdm;
    private String portfolio;
    private String division;
    private String periodBasis;
    private String version;
    private String fp1;
    private String fp2;
    private String instanceId;
}
