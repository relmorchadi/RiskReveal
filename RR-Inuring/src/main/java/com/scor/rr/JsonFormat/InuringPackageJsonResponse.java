package com.scor.rr.JsonFormat;

import com.scor.rr.JsonFormat.InuringNetworkDefinition;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InuringPackageJsonResponse {

    private int FormatCode;
    private int InuringPackageId;
    private Date CreationDate;
    private String Creator;
    private Date LastModificationDate;
    private String LastModifier;
    private String Other;
    private boolean groupedToMinimumGrainRP;
    private List<String> finalNodeGroupingCriteria;
    private String OutputDir;
    private InuringNetworkDefinition inuringNetworkDefinition;
    private CurrencyDefinition currencyDefinition;
}
