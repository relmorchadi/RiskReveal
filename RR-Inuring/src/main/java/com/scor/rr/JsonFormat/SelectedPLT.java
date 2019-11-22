package com.scor.rr.JsonFormat;

import lombok.Data;

import java.util.List;

@Data
public class SelectedPLT {
    private String filename;
    private String path;
    private String currency;
    private String targetCurrency;
    private int sourceName;
    private int targetRapId;
    private String targetRapCode;
    private String regionPeril;
    private String peril;
    private String grain;
    private int pltStructureCode;
    private List<String> properties;
}
