package com.scor.rr.JsonFormat;

import com.google.gson.JsonObject;
import lombok.Data;

import java.util.List;

//import javax.json.JsonObject;

@Data
public class ContractNodeList {

    private int index;
    private long id;
    private int level;
    private String contractType;
    private boolean isFinal;
    private List<Integer> inputNodeToLoad;
    private List<Integer> edgeToLoad;
    private String contractCurrencyCode;
    private List<Object> attributes;
}
