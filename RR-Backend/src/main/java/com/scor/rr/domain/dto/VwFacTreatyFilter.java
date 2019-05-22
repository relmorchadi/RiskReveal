package com.scor.rr.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VwFacTreatyFilter {
    public String keyword;
    public String id;
    public String analysisName;
    public String analysisCtrBusinessType;
    public String analysisCtrId;
    public Boolean analysisCtrEndorsementNmber;
    public String analysisCtrFacNumber;
    public String analysisCtrInsured;
    public String analysisCtrLabel;
    public Long analysisCtrLob;
    public Boolean analysisCtrOrderNumber;
    public Long analysisCtrSubsidiary;
    public Integer analysisCtrYear;
    public String assignDate;
    public String assignedTo;
    // public String lastUpdateDate;
    public String lastUpdatedBy;
    public String modellingSystemInstance;
}
